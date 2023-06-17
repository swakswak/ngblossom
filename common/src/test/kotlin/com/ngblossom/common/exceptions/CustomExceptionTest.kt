package com.ngblossom.common.exceptions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CustomExceptionTest {
    @Test
    fun testClientSideException() {
        val clientSideException = ClientSideException(
            "flower api",
            400,
            reason = "Bad Request"
        )
        assertNotNull(clientSideException)
        assertEquals("flower api", clientSideException.dependencyName)
        assertEquals(400, clientSideException.receivedCode)
        assertEquals("Bad Request", clientSideException.reason)
    }

    @Test
    fun testDependencyServerSideException() {
        val dependencyServerSideException = DependencyServerSideException(
            "flower api",
            500,
            reason = "Internal Server Error"
        )
        assertNotNull(dependencyServerSideException)
        assertEquals("flower api", dependencyServerSideException.dependencyName)
        assertEquals(500, dependencyServerSideException.receivedCode)
        assertEquals("Internal Server Error", dependencyServerSideException.reason)
    }
}