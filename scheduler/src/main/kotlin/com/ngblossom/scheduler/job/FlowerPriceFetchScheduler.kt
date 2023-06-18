package com.ngblossom.scheduler.job

import com.ngblossom.scheduler.service.FlowerPriceApiFetcher
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Component
import java.time.ZoneId

@Component
class FlowerPriceFetchScheduler(
    private val flowerPriceApiFetcher: FlowerPriceApiFetcher,
//    private val flowerPriceRepository: FlowerPriceRepository,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate
) {
    private val seoulZoneId = ZoneId.of("Asia/Seoul")

    // TODO: 구현
//    @Scheduled(cron = "\${flowerdata.cron}", zone = "\${flowerdata.cron.timezone}:Asia/Seoul")
//    @Transactional
//    suspend fun fetchFlowerPrice() {
//        val flowerPrices = flowerPriceApiFetcher.fetchFlowerData(LocalDate.now(seoulZoneId), FlowerType.FLORAL_BRANCH)
//        r2dbcEntityTemplate.insert<>()
//    }
}