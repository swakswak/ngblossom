package com.ngblossom.scheduler.infrastructure.rest

import com.ngblossom.common.domain.flowerprice.FlowerType
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import kotlin.test.assertEquals

class FlowerPriceRestApiFetcherTest {
    private var port: String = "8888"

    private val mockWebServer = MockWebServer()

    private lateinit var flowerPriceDataRestClient: FlowerPriceRestApiFetcher

    @BeforeEach
    fun setup() {
        mockWebServer.start(port.toInt())
        val baseUrl = mockWebServer.url("/").toString()
        flowerPriceDataRestClient = FlowerPriceRestApiFetcher(baseUrl, "my-service-key", WebClient.builder())
    }

    @AfterEach
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetFlowerData() = runBlocking {
        val mockRes = """
            {
            	"response": {
            		"resultCd": "0",
            		"resultMsg": "OK",
            		"numOfRows": "2",
            		"items": [{
            			"saleDate": "2018-08-16",
            			"flowerGubn": "절화",
            			"pumName": "백합",
            			"goodName": "메두사",
            			"lvNm": "특2",
            			"maxAmt": "1800",
            			"minAmt": "1800",
            			"avgAmt": "1800",
            			"totAmt": "88200",
            			"totQty": "49"
            		}, {
            			"saleDate": "2018-08-16",
            			"flowerGubn": "절화",
            			"pumName": "국화",
            			"goodName": "샤로트",
            			"lvNm": "상2",
            			"maxAmt": "1200",
            			"minAmt": "1200",
            			"avgAmt": "1200",
            			"totAmt": "48000",
            			"totQty": "40"
            		}]
            	}
            }
        """.trimIndent()
        val baseDate = LocalDate.of(2018, 8, 16)
        val flowerType = FlowerType.FLORAL_BRANCH
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .setBody(mockRes)
        )

        // Act
        val result = flowerPriceDataRestClient.fetchFlowerData(baseDate, flowerType)

        // Assert
        assertEquals("2018-08-16", result[0].saleDate.toString())
        assertEquals(2, result.size)
        assertEquals(FlowerType.FLORAL_BRANCH, result[0].flowerType)
        assertEquals("백합", result[0].itemName)
        assertEquals("메두사", result[0].cultivarName)
        assertEquals(1800, result[0].maxAmount)
        assertEquals(1800, result[0].minAmount)
        assertEquals(1800, result[0].averageAmount)
        assertEquals(88200, result[0].totalAmount)
        assertEquals(49, result[0].totalQuantity)

        assertEquals("2018-08-16", result[1].saleDate.toString())
        assertEquals(FlowerType.FLORAL_BRANCH, result[1].flowerType)
        assertEquals("국화", result[1].itemName)
        assertEquals("샤로트", result[1].cultivarName)
        assertEquals(1200, result[1].maxAmount)
        assertEquals(1200, result[1].minAmount)
        assertEquals(1200, result[1].averageAmount)
        assertEquals(48000, result[1].totalAmount)
        assertEquals(40, result[1].totalQuantity)
    }
}