package com.ngblossom.scheduler.service

import com.ngblossom.common.domain.flowerprice.FlowerPrice
import com.ngblossom.common.domain.flowerprice.FlowerType
import java.time.LocalDate

interface FlowerPriceApiFetcher {
    suspend fun fetchFlowerData(baseDate: LocalDate, flowerType: FlowerType): List<FlowerPrice>
}
