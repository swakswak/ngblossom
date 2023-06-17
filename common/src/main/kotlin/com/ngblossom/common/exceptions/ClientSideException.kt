package com.ngblossom.common.exceptions

class ClientSideException(
    val dependencyName: String,
    val receivedCode: Int,
    val reason: String? = null
) : RuntimeException("Dependency $dependencyName returned $receivedCode: $reason")