package com.spruce.challenge.report.accesslog.config

import com.spruce.challenge.report.accesslog.processor.HttpServerAccessLogProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Value("\${spruce.server.access.log.name}")
    val fileName: String = ""

    @Bean
    fun logProcessor(): HttpServerAccessLogProcessor {
        return HttpServerAccessLogProcessor(fileName)
    }
}
