package com.ngblossom.common.domain.flowerprice

import java.time.LocalDate

data class FlowerPrice(
    val saleDate: LocalDate,
    val flowerType: FlowerType,
    val itemName: String,
    val cultivarName: String,
    val grade: String,
    val maxAmount: Int,
    val minAmount: Int,
    val averageAmount: Int,
    val totalAmount: Int,
    val totalQuantity: Int
)
