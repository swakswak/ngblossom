package com.ngblossom.common.filter.logging

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.config.AppenderRef
import org.apache.logging.log4j.core.config.Configuration
import org.apache.logging.log4j.core.config.LoggerConfig
import org.apache.logging.log4j.core.layout.JsonLayout
import org.apache.logging.log4j.message.Message
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.mock.http.server.reactive.MockServerHttpRequest
import org.springframework.mock.web.server.MockServerWebExchange
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import kotlin.test.assertEquals


class LoggerFilterTest {
    private val logMessages: MutableList<Message> = mutableListOf()

    @BeforeEach
    fun setUp() {
        val loggerName = LoggerFilter::class.java.canonicalName
        val context: LoggerContext = LogManager.getContext(false) as LoggerContext
        val config: Configuration = context.configuration
        val jsonLayout = JsonLayout.newBuilder()
            .setCompact(true)
            .setObjectMessageAsJsonObject(true)
            .setProperties(true)
            .setEventEol(true)
            .build()
        val appender: AbstractAppender = object : AbstractAppender(loggerName, null, jsonLayout, false, null) {
            override fun append(event: LogEvent) {
                logMessages.add(event.message)
            }
        }
        appender.start()
        config.addAppender(appender)
        val ref = AppenderRef.createAppenderRef("TestAppender", null, null)
        val refs = arrayOf(ref)
        val loggerConfig: LoggerConfig = LoggerConfig.newBuilder()
            .withAdditivity(false)
            .withLevel(Level.ALL)
            .withLoggerName(loggerName)
            .withIncludeLocation("true")
            .withRefs(refs)
            .withProperties(null)
            .withConfig(config)
            .withtFilter(null)
            .build()
        loggerConfig.addAppender(appender, null, null)
        config.addLogger(loggerName, loggerConfig)
        context.updateLoggers()
    }

    @AfterEach
    fun tearDown() {
        val context: LoggerContext = LogManager.getContext(false) as LoggerContext
        val config: Configuration = context.configuration
        config.getAppender<AbstractAppender>("TestAppender")?.stop()
        config.getLoggerConfig("TestAppender")?.removeAppender("TestAppender")
        config.removeLogger(LoggerFilter::class.java.canonicalName)
        context.updateLoggers()
    }

    @Test
    fun testFilter() {
        val filter = LoggerFilter()
        val mockRequest = MockServerHttpRequest
            .get("/test/1")
            .header("x-test-header", "test-header-value")
            .header("Authorization", "Bearer test-token")
        val exchange = MockServerWebExchange.builder(mockRequest)
            .sessionManager {
                it.response.setStatusCode(HttpStatus.OK)
                Mono.empty()
            }
            .build()
        val filterChain = WebFilterChain { Mono.empty() }

        filter.filter(exchange, filterChain).block()
        assertEquals(1, logMessages.size)
        assertEquals(1, logMessages[0].parameters.size)
        assertEquals(
            linkedMapOf(
                "class" to LoggerFilter::class.java.canonicalName,
                "http" to StampPerRequest(
                    RequestStamp(
                        "GET",
                        "/test/1",
                        LinkedMultiValueMap(),
                        HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                            add("x-test-header", "test-header-value")
                            add("Authorization", "Bearer [masked]")
                        })
                    ),
                    ResponseStamp(
                        HttpStatus.OK,
                        HttpHeaders.writableHttpHeaders(HttpHeaders())
                    )
                )
            ), logMessages[0].parameters[0]
        )

    }
}