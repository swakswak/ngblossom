package com.ngblossom.common.filter.logging

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.util.LinkedMultiValueMap

class StampPerRequestTest {
    @Test
    fun testStampPerRequest() {
        val stampPerRequest = StampPerRequest(
            RequestStamp(
                "GET",
                "/test/1",
                LinkedMultiValueMap<String, String>().apply {
                    add("qskey1", "value1")
                    add("qskey1", "value2")
                    add("qskey2", "value3")
                },
                HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                    add("hkey1", "value1")
                    add("hkey1", "value2")
                    add("hkey2", "value3")
                })
            ),
            ResponseStamp(
                HttpStatusCode.valueOf(200),
                HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                    add("hkey1", "value1")
                    add("hkey1", "value2")
                    add("hkey2", "value3")
                })
            )
        )

        assertEquals("GET", stampPerRequest.request.method)
        assertEquals("/test/1", stampPerRequest.request.path)
        assertEquals(
            LinkedMultiValueMap<String, String>().apply {
                add("qskey1", "value1")
                add("qskey1", "value2")
                add("qskey2", "value3")
            },
            stampPerRequest.request.query
        )
        assertEquals(
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey1", "value2")
                add("hkey2", "value3")
            }),
            stampPerRequest.request.headers
        )
        assertEquals(HttpStatusCode.valueOf(200), stampPerRequest.response.response)
        assertEquals(
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey1", "value2")
                add("hkey2", "value3")
            }),
            stampPerRequest.response.headers
        )
    }
}