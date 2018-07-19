package com.spruce.challenge.report.accesslog.controller

import com.spruce.challenge.report.accesslog.processor.HttpServerAccessLogProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping(value = "/api/report")
class ReportController {

    @Autowired
    private val logProcessor: HttpServerAccessLogProcessor? = null

    @RequestMapping(
            method = arrayOf(RequestMethod.GET),
            value = "/unique-page-hits/{date}",
            produces = arrayOf(APPLICATION_JSON_VALUE))
    fun getDailyPagesByUniqueHits(
            @PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate): List<String> {
        return logProcessor!!.getDailyPagesByUniqueHits(date)
    }

    @RequestMapping(
            method = arrayOf(RequestMethod.GET),
            value = "/pages-by-number-of-users/{date}",
            produces = arrayOf(APPLICATION_JSON_VALUE))
    fun getDailyPagesByNumberOfUsers(
            @PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate): Map<String, Int> {
        return logProcessor!!.getDailyPagesByNumberOfUsers(date)
    }

    @RequestMapping(
            method = arrayOf(RequestMethod.GET),
            value = "/number-of-users/{date}/{page}",
            produces = arrayOf(APPLICATION_JSON_VALUE))
    fun getDailyPagesByNumberOfUsers(
            @PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
            @PathVariable(name = "page") page: String): Int? {
        return logProcessor!!.getDailyPagesByNumberOfUsers(date).get(page)
    }

    @RequestMapping(
            method = arrayOf(RequestMethod.GET),
            value = "/all-users-unique-page-views/{date}",
            produces = arrayOf(APPLICATION_JSON_VALUE))
    fun getDailyUsersByUniquePageViews(
            @PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate): Map<String, List<String>> {
        return logProcessor!!.getDailyUsersByUniquePageViews(date)
    }

    @RequestMapping(
            method = arrayOf(RequestMethod.GET),
            value = "/user-unique-page-views/{date}/{userId}",
            produces = arrayOf(APPLICATION_JSON_VALUE))
    fun getDailyUsersByUniquePageViews(
            @PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
            @PathVariable(name = "userId") userId: String): List<String>? {
        return logProcessor?.getDailyUsersByUniquePageViews(date)?.get(userId)
    }
    }