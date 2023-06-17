package com.ngblossom.common.filter.logging

import org.springframework.http.HttpHeaders
import org.springframework.util.MultiValueMap

internal data class RequestStamp(
    val method: String, val path: String, val query: MultiValueMap<String, String>, private val _headers: HttpHeaders
) {
    val headers: HttpHeaders = HttpHeaders().apply {
        putAll(_headers)
        if (containsKey("Authorization")) {
            set("Authorization", "[masked]")
        }
    }
}