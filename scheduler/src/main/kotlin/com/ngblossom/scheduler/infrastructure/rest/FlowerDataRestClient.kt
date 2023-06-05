package com.ngblossom.scheduler.infrastructure.rest

import com.ngblossom.extensions.toApiDateFormat
import com.ngblossom.domain.flowerprice.FlowerType
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.time.LocalDate

class FlowerDataRestClient(
    @Value("\${flowerdata.api.base-url}") private val baseUrl: String,
    @Value("\${flowerdata.api.service-key}") private val serviceKey: String,
    private val webClient: WebClient = WebClient.builder().baseUrl(baseUrl).build(),
) {
    suspend fun getFlowerData(baseDate: LocalDate, flowerType: FlowerType): FlowerPriceRestResponse {
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
            .response
    }
}
