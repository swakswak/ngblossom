package com.ngblossom.common.filter.logging

import org.springframework.http.HttpHeaders
import org.springframework.util.MultiValueMap

internal data class RequestStamp(
    val method: String, val path: String, val query: MultiValueMap<String, String>, private val _headers: HttpHeaders
) {
    val headers = _headers.map {
        when (it.key) {
            "Authorization" -> it.key to "[masked]"
            else -> it
        }
    }
}