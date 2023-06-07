package com.ngblossom.common.domain.flowerprice

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class FlowerPriceTest {
    @Test
    fun `should not null`() {
        val flowerPrice = FlowerPrice(
            saleDate = LocalDate.of(2021, 1, 1),
            flowerType = FlowerType.FLORAL_BRANCH,
            itemName = "장미",
            cultivarName = "블론디",
            grade = "특1",
            maxAmount = 1,
            minAmount = 1,
            averageAmount = 1,
            totalAmount = 1,
            totalQuantity = 1
        )
        assertNotNull(flowerPrice)
        assertEquals(LocalDate.of(2021, 1, 1), flowerPrice.saleDate)
        assertEquals(FlowerType.FLORAL_BRANCH, flowerPrice.flowerType)
        assertEquals("장미", flowerPrice.itemName)
        assertEquals("블론디", flowerPrice.cultivarName)
        assertEquals("특1", flowerPrice.grade)
        assertEquals(1, flowerPrice.maxAmount)
        assertEquals(1, flowerPrice.minAmount)
        assertEquals(1, flowerPrice.averageAmount)
        assertEquals(1, flowerPrice.totalAmount)
        assertEquals(1, flowerPrice.totalQuantity)
    }
}