package com.ngblossom.scheduler.infrastructure.rest

import com.ngblossom.domain.flowerprice.FlowerPrice
import com.ngblossom.domain.flowerprice.FlowerType
import com.ngblossom.extensions.toApiDateFormat
import com.ngblossom.scheduler.service.FlowerPriceDataClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
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
            .retrieve()
            .awaitBody<FlowerPriceRestResponseBody>()
            .toFlowerPrices()
    }
}
