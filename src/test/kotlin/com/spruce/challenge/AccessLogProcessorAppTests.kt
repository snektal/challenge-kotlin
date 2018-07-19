package com.spruce.challenge

import com.spruce.challenge.report.accesslog.processor.HttpServerAccessLogProcessor
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RunWith(SpringRunner::class)
@SpringBootTest
class AccessLogProcessorAppTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun getDailyPagesByUniqueHits_ForGivenDate_ShouldReturnFourDailyUniquePageHits() {
        val dateKey = LocalDate.parse("2017-09-29", DateTimeFormatter.ISO_LOCAL_DATE)
        LOGGER.info("date key passed: {}", dateKey)

        val dailyPagesByUniqueHits = logProcessor.getDailyPagesByUniqueHits(dateKey)

        val expectedHits = 4
        val actualHits = dailyPagesByUniqueHits.size
        LOGGER.info("date key = {}", dateKey)
        LOGGER.info("expectedHits: {}, actualHits: {}", expectedHits, actualHits)
        assert(expectedHits.toLong() == actualHits.toLong())

    }

    @Test
    fun getDailyPagesByNumberOfUsers_ForGivenDateAndPage_ShouldReturnTwoUsers() {
        val dateKey = LocalDate.parse("2017-09-29", DateTimeFormatter.ISO_LOCAL_DATE)
        LOGGER.info("date key passed: {}", dateKey)

        val dailyPagesByNumberOfUsers: Map<String, Int> = logProcessor.getDailyPagesByNumberOfUsers(dateKey)

        val path = "index.html"
        LOGGER.info("page (path) passed: {}", path)

        val expectedNumberOfUsers = 2
        val actualNumberOfUsers = dailyPagesByNumberOfUsers.get(path)

        LOGGER.info("expectedNumberOfUsers: {}, actualNumberOfUsers: {}", expectedNumberOfUsers, actualNumberOfUsers)
        assert(expectedNumberOfUsers.toLong() == actualNumberOfUsers!!.toLong())

    }

    @Test
    fun getDailyUsersByUniquePageViews_ForGivenDateAndUser_ShouldReturnOneDailyUniquePageViewByUser() {
        val dateKey = LocalDate.parse("2017-09-28", DateTimeFormatter.ISO_LOCAL_DATE)
        LOGGER.info("date key passed: {}", dateKey)

        val usersByUniquePageViews = logProcessor.getDailyUsersByUniquePageViews(dateKey)

        val userIdKey = "04a5d9a7-0a76-47a8-abd3-9e39a1abce51"
        LOGGER.info("user ID key passed: {}", userIdKey)

        val expectedUniquePageViews = 1

        val actualUniquePageViews = usersByUniquePageViews[userIdKey]!!.size

        LOGGER.info("expectedUniquePageViews: {}, actualHits: {}", expectedUniquePageViews, actualUniquePageViews)
        assert(expectedUniquePageViews.toLong() == actualUniquePageViews.toLong())

    }

    val logProcessor: HttpServerAccessLogProcessor = HttpServerAccessLogProcessor(fileName)

    companion object {
        private val LOGGER = LoggerFactory.getLogger("HttpServerAccessLogProcessorTest")
        private const val fileName: String = "src/test/resources/testlog.csv"

    }



}
