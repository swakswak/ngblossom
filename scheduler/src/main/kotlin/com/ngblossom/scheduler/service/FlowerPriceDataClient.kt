package com.ngblossom.scheduler.service

import com.ngblossom.domain.flowerprice.FlowerPrice
import com.ngblossom.domain.flowerprice.FlowerType
import java.time.LocalDate

interface FlowerPriceDataClient {
    suspend fun getFlowerData(baseDate: LocalDate, flowerType: FlowerType): List<FlowerPrice>
}
