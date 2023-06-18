package com.ngblossom.common.filter.logging

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode

internal data class ResponseStamp(val statusCode: HttpStatusCode?, val headers: HttpHeaders)