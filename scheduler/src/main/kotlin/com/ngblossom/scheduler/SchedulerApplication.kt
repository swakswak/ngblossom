package com.ngblossom.scheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class SchedulerApplication

fun main(args: Array<String>) {
    runApplication<SchedulerApplication>(*args)
}
