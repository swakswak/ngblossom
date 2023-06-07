package com.ngblossom.common.scheduler.infrastructure.rest

import com.ngblossom.common.domain.flowerprice.FlowerPrice
import com.ngblossom.common.domain.flowerprice.FlowerType
import com.ngblossom.common.exceptions.DependencyServerErrorException
import com.ngblossom.common.extensions.toApiDateFormat
import com.ngblossom.common.scheduler.service.FlowerPriceDataClient
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.time.LocalDate

@Component
class FlowerPriceDataRestClient(
    @Value("\${flowerdata.api.base-url}") private val baseUrl: String,
    @Value("\${flowerdata.api.service-key}") private val serviceKey: String,
    webClientBuilder: WebClient.Builder,
) : FlowerPriceDataClient {
    private val webClient = webClientBuilder.baseUrl(baseUrl).build()

    override suspend fun getFlowerData(baseDate: LocalDate, flowerType: FlowerType): List<FlowerPrice> {
        return webClient.get()
            .uri {
                it.queryParam("kind", "f001")
                    .queryParam("baseDate", baseDate.toApiDateFormat())
                    .queryParam("flowerGubn", flowerType)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("dataType", "json")
                    .queryParam("countPerPage", "100000")
                    .build()
            }
            .exchangeToMono<FlowerPriceRestResponseBody> {
                when(it.statusCode().isError) {
                    true -> Mono.error(DependencyServerErrorException(baseUrl, it.statusCode().value()))
                    false -> it.bodyToMono(FlowerPriceRestResponseBody::class.java)
                }
            }
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
            .awaitSingle()
            .toFlowerPrices()
    }
}
