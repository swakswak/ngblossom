package com.ngblossom.scheduler.job

import com.ngblossom.common.domain.flowerprice.FlowerType
import com.ngblossom.scheduler.service.FlowerPriceApiFetcher
import com.ngblossom.scheduler.service.rdb.FlowerPriceRepository
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.insert
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.kotlin.core.publisher.toFlux
import java.time.LocalDate
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