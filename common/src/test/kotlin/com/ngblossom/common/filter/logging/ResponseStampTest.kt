package com.ngblossom.common.filter.logging

import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ResponseStampTest {
    @Test
    fun testResponseStamp() {
        val responseStamp = ResponseStamp(
            HttpStatus.OK,
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
            })
        )

        assertEquals(HttpStatus.OK, responseStamp.statusCode)
        assertEquals(
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
            }),
            responseStamp.headers
        )
    }

    @Test
    fun testEquals() {
        val responseStampEqual1 = ResponseStamp(
            HttpStatus.OK,
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
            })
        )
        val responseStampEqual2 = ResponseStamp(
            HttpStatus.OK,
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
                add("hkey3", "value3")
            })
        )
        val responseStampNotEqual1 = ResponseStamp(
            HttpStatus.OK,
            HttpHeaders.writableHttpHeaders(HttpHeaders().apply {
                add("hkey1", "value1")
                add("hkey2", "value2")
            })
        )

        assertEquals(responseStampEqual1, responseStampEqual2)
        assertEquals(responseStampEqual1.hashCode(), responseStampEqual2.hashCode())
        assertEquals(responseStampEqual1.toString(), responseStampEqual2.toString())

        assertNotEquals(responseStampEqual1, responseStampNotEqual1)
        assertNotEquals(responseStampEqual1.hashCode(), responseStampNotEqual1.hashCode())
        assertNotEquals(responseStampEqual1.toString(), responseStampNotEqual1.toString())
    }
}