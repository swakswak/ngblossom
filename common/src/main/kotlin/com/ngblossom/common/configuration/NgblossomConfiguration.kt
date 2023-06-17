package com.ngblossom.common.configuration

import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime
import java.util.*

@Configuration
class NgblossomConfiguration {
    private val log: Logger = LoggerFactory.getLogger(NgblossomConfiguration::class.java)

    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }

    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        log.info("Timezone set to UTC ({})", LocalDateTime.now())
    }
}