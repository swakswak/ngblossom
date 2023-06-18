package com.ngblossom.scheduler.infrastructure.rest

import com.ngblossom.common.domain.flowerprice.FlowerPrice
import com.ngblossom.common.domain.flowerprice.FlowerType
import com.ngblossom.common.exceptions.DependencyServerSideException
import com.ngblossom.common.extensions.toApiDateFormat
import com.ngblossom.scheduler.service.FlowerPriceApiFetcher
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.time.LocalDate

@Component
internal class FlowerPriceRestApiFetcher(
    @Value("\${flowerdata.api.base-url}") private val baseUrl: String,
    @Value("\${flowerdata.api.service-key}") private val serviceKey: String,
    webClientBuilder: WebClient.Builder,
) : FlowerPriceApiFetcher {
    private val webClient = webClientBuilder.baseUrl(baseUrl).build()

    override suspend fun fetchFlowerData(baseDate: LocalDate, flowerType: FlowerType): List<FlowerPrice> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.queryParam("kind", "f001")
                    .queryParam("baseDate", baseDate.toApiDateFormat())
                    .queryParam("flowerGubn", flowerType)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("dataType", "json")
                    .queryParam("countPerPage", "100000")
                    .build()
            }
            .exchangeToMono {
                when (it.statusCode().is5xxServerError) {
                    true -> Mono.error(DependencyServerSideException(baseUrl, it.statusCode().value()))
                    false -> it.bodyToMono(FlowerPriceRestResponseBody::class.java)
                }
            }
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
            .awaitSingle()
            .toFlowerPrices()
    }
}
