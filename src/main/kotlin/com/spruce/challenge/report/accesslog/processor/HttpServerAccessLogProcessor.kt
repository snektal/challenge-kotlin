package com.spruce.challenge.report.accesslog.processor

import com.spruce.challenge.report.accesslog.model.HttpServerAccessLogEntry
import java.io.File
import java.time.LocalDate

class HttpServerAccessLogProcessor(private val logFileAbsolutePath: String) {


    fun getLogEntriesGroupedByDate(): Map<LocalDate, List<HttpServerAccessLogEntry>> {
        // read file as a stream
        // skip first HEADER line
        // map line to HttpServerAccessLogEntry
        // group all entries by date
        return File(logFileAbsolutePath).useLines { lines ->
            lines.filter { !it.startsWith("Path,User,Timestamp") }
                    .map { HttpServerAccessLogEntry(it) }
                    .groupBy { it.date }
        }
    }

    fun getDailyPagesByUniqueHits(date: LocalDate): List<String> {
        var entriesGroupedByDate: Map<LocalDate, List<HttpServerAccessLogEntry>> = getLogEntriesGroupedByDate()

        if (entriesGroupedByDate.get(date) == null) {
            return emptyList()
        }
        return entriesGroupedByDate[date]!!.map { it.path }.distinct()
    }

    fun getDailyPagesByNumberOfUsers(date: LocalDate): Map<String, Int> {
        var entriesGroupedByDate: Map<LocalDate, List<HttpServerAccessLogEntry>> = getLogEntriesGroupedByDate()

        if (entriesGroupedByDate.get(date) == null) {
            return emptyMap()
        }

        val dailyPagesByNumberOfUsers: HashMap<String, Int> = HashMap()
        val pageNameToUserMap: HashMap<String, ArrayList<String>> = HashMap()

        entriesGroupedByDate[date]!!.forEach {
            if (dailyPagesByNumberOfUsers.containsKey(it.path)) {
                if (!pageNameToUserMap[it.path]!!.contains(it.userId)) {
                    dailyPagesByNumberOfUsers.compute(it.path, { _, v -> v!! + 1 })
                }

            } else {
                pageNameToUserMap.put(it.path, ArrayList())
                pageNameToUserMap[it.path]!!.add(it.userId)
                dailyPagesByNumberOfUsers.computeIfAbsent(it.path, { _ -> 1 })
            }

        }
        println(dailyPagesByNumberOfUsers)
        return dailyPagesByNumberOfUsers
    }


    fun getDailyUsersByUniquePageViews(date: LocalDate): Map<String, List<String>> {
        var entriesGroupedByDate: Map<LocalDate, List<HttpServerAccessLogEntry>> = getLogEntriesGroupedByDate()

        if (entriesGroupedByDate.get(date) == null) {
            return emptyMap()
        }

        val usersByUniquePageViews: HashMap<String, ArrayList<String>> = HashMap()

        entriesGroupedByDate[date]!!.forEach {
            if (!usersByUniquePageViews.containsKey(it.userId)) {
                usersByUniquePageViews.put(it.userId, ArrayList())
            }
            if (!usersByUniquePageViews[it.userId]!!.contains(it.path)) {
                usersByUniquePageViews[it.userId]!!.add(it.path)
            }
        }

        println(usersByUniquePageViews)
        return usersByUniquePageViews

    }

}
