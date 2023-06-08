package com.ngblossom.scheduler.service.rdb

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface FlowerPriceRepository: R2dbcRepository<FlowerPriceEntity, Long> {
    fun saveAll(entities: List<FlowerPriceEntity>)
}