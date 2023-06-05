package com.ngblossom.domain.flowerprice

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class FlowerTypeTest {
    @Test
    fun `should not null`() {
        val floralBranch = FlowerType.fromDisplayName("절화")
        assertNotNull(floralBranch)
        assertEquals(FlowerType.FLORAL_BRANCH, floralBranch)
        assertEquals(1, floralBranch.value)
        assertEquals("절화", floralBranch.displayName)
    }

    @Test
    fun `should throw IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) {
            FlowerType.fromDisplayName("잘못된 화훼부류명")
        }
    }
}
