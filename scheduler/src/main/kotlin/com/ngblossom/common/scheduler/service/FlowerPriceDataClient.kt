package com.ngblossom.common.scheduler.service

import com.ngblossom.common.domain.flowerprice.FlowerPrice
import com.ngblossom.common.domain.flowerprice.FlowerType
import java.time.LocalDate

interface FlowerPriceDataClient {
    suspend fun getFlowerData(baseDate: LocalDate, flowerType: FlowerType): List<FlowerPrice>
}
