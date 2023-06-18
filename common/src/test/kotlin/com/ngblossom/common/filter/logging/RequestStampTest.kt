package com.ngblossom.common.filter.logging

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.util.LinkedMultiValueMap

class RequestStampTest {
    @Test
    fun testRequestStamp() {
        val requestStamp = RequestStamp(
            "GET",
            "/test/1",
            LinkedMultiValueMap<String, String>().apply {
                add("qskey1", "value1")
                add("qskey2", "value2")
                add("qskey3", "value3")
            },
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
                add("Authorization", "Bearer test-token")
            })
        )

        assertEquals("GET", requestStamp.method)
        assertEquals("/test/1", requestStamp.path)
        assertEquals(
            LinkedMultiValueMap<String, String>().apply {
                add("qskey1", "value1")
                add("qskey2", "value2")
                add("qskey3", "value3")
            },
            requestStamp.query
        )
        assertEquals(
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
                add("Authorization", "[masked]")
            }),
            requestStamp.headers
        )
    }

    @Test
    fun testEquals() {
        val requestStampEqual1 = RequestStamp(
            "GET",
            "/test/1",
            LinkedMultiValueMap<String, String>().apply {
                add("qskey1", "value1")
                add("qskey2", "value2")
                add("qskey3", "value3")
            },
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
                add("Authorization", "Bearer test-token")
            })
        )

        val requestStampEqual2 = RequestStamp(
            "GET",
            "/test/1",
            LinkedMultiValueMap<String, String>().apply {
                add("qskey1", "value1")
                add("qskey2", "value2")
                add("qskey3", "value3")
            },
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
                add("Authorization", "Bearer test-token")
            })
        )

        val requestStampNotEqual = RequestStamp(
            "GET",
            "/test/1",
            LinkedMultiValueMap<String, String>().apply {
                add("qskey1", "value1")
                add("qskey2", "value2")
                add("qskey3", "value3")
            },
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
            })
        )

        assertEquals(requestStampEqual1, requestStampEqual2)
        assertNotEquals(requestStampEqual1, requestStampNotEqual)
        assertNotEquals(requestStampEqual2, requestStampNotEqual)

        assertEquals(requestStampEqual1.hashCode(), requestStampEqual2.hashCode())
        assertNotEquals(requestStampEqual1.hashCode(), requestStampNotEqual.hashCode())
        assertNotEquals(requestStampEqual2.hashCode(), requestStampNotEqual.hashCode())

        assertEquals(requestStampEqual1.toString(), requestStampEqual2.toString())
        assertNotEquals(requestStampEqual1.toString(), requestStampNotEqual.toString())
        assertNotEquals(requestStampEqual2.toString(), requestStampNotEqual.toString())
    }
}