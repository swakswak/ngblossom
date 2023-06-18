package com.ngblossom.common.filter.logging

import org.springframework.http.HttpHeaders
import org.springframework.util.MultiValueMap

internal class RequestStamp(
    val method: String, val path: String, val query: MultiValueMap<String, String>, headers: HttpHeaders
) {
    val headers: HttpHeaders

    init {
        this.headers = HttpHeaders().apply {
            putAll(headers)
            if (containsKey("Authorization")) {
                set("Authorization", "[masked]")
            }
        }
    }

    override fun toString(): String = "RequestStamp(method='$method', path='$path', query=$query, headers=$headers)"

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        else -> {
            other as RequestStamp
            when {
                method != other.method -> false
                path != other.path -> false
                query != other.query -> false
                else -> headers == other.headers
            }
        }
    }

    override fun hashCode(): Int {
        var result = method.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + query.hashCode()
        result = 31 * result + headers.hashCode()
        return result
    }
}