package com.ngblossom.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.ngblossom.common")
@ComponentScan("com.ngblossom.scheduler")
class CoreApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
        .sources(CoreApplication::class.java)
        .properties("spring.config.name: application, scheduler")
        .build()
        .run()
}
