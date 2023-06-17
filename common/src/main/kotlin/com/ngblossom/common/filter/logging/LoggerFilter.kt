package com.ngblossom.common.filter.logging

import com.ngblossom.common.extensions.info
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class LoggerFilter(
    private val log: Logger = LogManager.getLogger(LoggerFilter::class.java)
) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        log.info(
            "class" to this.javaClass.canonicalName,
            "http" to StampPerRequest(
                RequestStamp(
                    exchange.request.method.toString(),
                    exchange.request.path.value(),
                    exchange.request.queryParams,
                    exchange.request.headers
                ),
                ResponseStamp(exchange.response.statusCode, exchange.response.headers)
            )
        )
        return chain.filter(exchange)
    }
}
