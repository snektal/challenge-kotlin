package com.spruce.challenge.report.accesslog.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Objects

data class HttpServerAccessLogEntry(val line: String) {
    var path: String = ""
        private set
    var userId: String = ""
        private set
    var date: LocalDate = LocalDate.MIN
        private set

    init {
        val logEntry = line.split(COMMA_DELIMITER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (!logEntry[0].isEmpty()) {
            this.path = logEntry[0]
            if (path.startsWith("/")) {
                path = path.substring(1)
            }
        }

        if (!logEntry[1].isEmpty())
            this.userId = logEntry[1]

        if (!logEntry[2].isEmpty()) {
            val dateTimeWithoutMillis = logEntry[2].split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            this.date = LocalDate.parse(dateTimeWithoutMillis, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

    companion object {
        private val COMMA_DELIMITER = ","
    }
}

