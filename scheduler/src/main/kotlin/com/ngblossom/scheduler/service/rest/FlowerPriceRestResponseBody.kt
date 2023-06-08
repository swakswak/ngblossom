package com.ngblossom.scheduler.service.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.ngblossom.common.domain.flowerprice.FlowerPrice
import com.ngblossom.common.domain.flowerprice.FlowerType
import com.ngblossom.common.extensions.dateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal data class FlowerPriceRestResponseBody(
    @JsonProperty("response") val response: FlowerPriceRestResponse,
) {
    fun toFlowerPrices(): List<FlowerPrice> {
        return response.items.map {
            FlowerPrice(
                saleDate = LocalDate.parse(it.saleDate, DateTimeFormatter.ofPattern(dateFormat())),
                flowerType = FlowerType.fromDisplayName(it.flowerGubn),
                itemName = it.pumName,
                cultivarName = it.goodName,
                grade = it.lvNm,
                maxAmount = it.maxAmt.toInt(),
                minAmount = it.minAmt.toInt(),
                averageAmount = it.avgAmt.toInt(),
                totalAmount = it.totAmt.toInt(),
                totalQuantity = it.totQty.toInt(),
            )
        }
    }
}

internal data class FlowerPriceRestResponse(
    @JsonProperty("resultCd") val resultCd: String,
    @JsonProperty("resultMsg") val resultMsg: String,
    @JsonProperty("numOfRows") val numOfRows: String,
    @JsonProperty("items") val items: List<FlowerPriceItem>,
)

internal data class FlowerPriceItem(
    @JsonProperty("saleDate") val saleDate: String,
    @JsonProperty("flowerGubn") val flowerGubn: String,
    @JsonProperty("pumName") val pumName: String,
    @JsonProperty("goodName") val goodName: String,
    @JsonProperty("lvNm") val lvNm: String,
    @JsonProperty("maxAmt") val maxAmt: String,
    @JsonProperty("minAmt") val minAmt: String,
    @JsonProperty("avgAmt") val avgAmt: String,
    @JsonProperty("totAmt") val totAmt: String,
    @JsonProperty("totQty") val totQty: String,
)
