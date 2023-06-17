package com.ngblossom.core.apis

import com.ngblossom.common.exceptions.DependencyServerSideException
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration.*

@RestController
@RequestMapping("/status")
class StatusTestController(
    private val webClient: WebClient = WebClient.builder().baseUrl("https://httpstat.us").build()
) {
    private val retries: Long = 3

    @RequestMapping("/{statusCode}")
    suspend fun test(@PathVariable statusCode: Int): String = webClient.get()
        .uri("/$statusCode")
        .exchangeToMono {
            when (it.statusCode().is5xxServerError) {
                true -> Mono.error(DependencyServerSideException("https://httpstat.us", it.statusCode().value()))
                false -> it.bodyToMono(String::class.java)
            }
        }
        .retryWhen(
            Retry.fixedDelay(retries, ofMillis(250))
                .doAfterRetry {
                    if (it.totalRetries() == retries - 1) {
                        println(it.failure())
                        it.failure()
                    }
                }
        )
        .awaitSingle()
}
