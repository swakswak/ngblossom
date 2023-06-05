package com.ngblossom.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun dateFormat(): String = "yyyy-MM-dd"
fun LocalDate.toApiDateFormat(): String = this.format(DateTimeFormatter.ofPattern(dateFormat()))
