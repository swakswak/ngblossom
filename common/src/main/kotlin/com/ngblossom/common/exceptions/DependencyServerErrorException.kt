package com.ngblossom.common.exceptions

class DependencyServerErrorException(
    private val dependencyName: String,
    private val receivedCode: Int,
    private val reason: String? = null
) : RuntimeException("Dependency $dependencyName returned $receivedCode: $reason")
