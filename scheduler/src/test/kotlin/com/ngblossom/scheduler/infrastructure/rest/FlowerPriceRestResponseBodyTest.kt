package com.ngblossom.scheduler.infrastructure.rest

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FlowerPriceRestResponseBodyTest {
    @Test
    fun testFlowerPriceItem() {
        val lily = FlowerPriceItem(
            "2023-06-19",
            "절화",
            "백합",
            "메두사",
            "특2",
            "1800",
            "1800",
            "1800",
            "88200",
            "49",
        )
        val chrysanthemum = FlowerPriceItem(
            "2023-06-19",
            "절화",
            "국화",
            "샤로트",
            "상2",
            "1200",
            "1200",
            "1200",
            "48000",
            "1",
        )
        val items = listOf(lily, chrysanthemum)
        val flowerPriceRestResponse = FlowerPriceRestResponse(
            "0",
            "OK",
            items.size.toString(),
            items
        )
        val flowerPriceRestResponseBody = FlowerPriceRestResponseBody(flowerPriceRestResponse)

        assertEquals("2", flowerPriceRestResponseBody.response.numOfRows)
        assertEquals("0", flowerPriceRestResponseBody.response.resultCd)
        assertEquals("OK", flowerPriceRestResponseBody.response.resultMsg)
        assertEquals(2, flowerPriceRestResponseBody.response.items.size)
        assertEquals(FlowerPriceItem(
            "2023-06-19",
            "절화",
            "백합",
            "메두사",
            "특2",
            "1800",
            "1800",
            "1800",
            "88200",
            "49",
        ), flowerPriceRestResponseBody.response.items[0])
        assertEquals(FlowerPriceItem(
            "2023-06-19",
            "절화",
            "국화",
            "샤로트",
            "상2",
            "1200",
            "1200",
            "1200",
            "48000",
            "1",
        ), flowerPriceRestResponseBody.response.items[1])
    }
}