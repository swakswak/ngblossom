package com.ngblossom.scheduler.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class SchedulerConfiguration {
    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }
}