package com.ngblossom.common.extensions

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

class LocalDateTimeExtensionsTest {

    @Test
    fun testDateFormat() {
        assertEquals("yyyy-MM-dd", dateFormat())
    }

    @Test
    fun toApiDateFormat() {
        assertEquals("2021-01-01", LocalDate.of(2021, 1, 1).toApiDateFormat())
        assertEquals("9999-12-31", LocalDate.of(9999, 12, 31).toApiDateFormat())
    }
}