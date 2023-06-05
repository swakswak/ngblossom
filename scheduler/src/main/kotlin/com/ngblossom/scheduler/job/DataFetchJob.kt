package com.ngblossom.scheduler.job

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class DataFetchJob(
    @Value("\${flowerdata.api.base-url}") private val baseUrl: String,
    @Value("\${flowerdata.api.service-key}") private val serviceKey: String,
    private val webClient: WebClient = WebClient.builder()
        .baseUrl(baseUrl).build()
//        .attribute("kind", "f001")
//        .attribute("baseDate", Local)
//        .attribute("serviceKey", serviceKey)
//        .attribute("dataType", "json")
//        .attribute("countPerPage", "100000")

) : Job {
    override fun execute(context: JobExecutionContext?) {
        TODO("Not yet implemented")
    }
}