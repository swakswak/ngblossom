package com.ngblossom.common.filter.logging

internal data class StampPerRequest(val request: RequestStamp, val response: ResponseStamp)