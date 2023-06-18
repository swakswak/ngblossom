package com.ngblossom.scheduler.infrastructure.rdb

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("flower")
data class FlowerPriceEntity(
    @Id
    val id: Long,
    val saleDate: String,
    val flowerType: String,
    val itemName: String,
    val cultivarName: String,
    val grade: String,
    val maxAmount: Long,
    val minAmount: Long,
    val averageAmount: Long,
    val totalAmount: Long,
    val totalQuantity: Long
) {
}
