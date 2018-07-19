package com.spruce.challenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccessLogProcessorApp

fun main(args: Array<String>) {
    runApplication<AccessLogProcessorApp>(*args)
}
