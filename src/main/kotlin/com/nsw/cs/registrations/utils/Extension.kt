package com.nsw.cs.registrations.utils

/**
 * Utils class providing convenient factory and helper methods.
 */

import com.nsw.cs.registrations.utils.Constants.Companion.DATE_FORMAT_PATTERN
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

val FORMATTER = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
val SIMPLE_FORMATTER = ofPattern("yyyyMMdd")


/**
 * Converts `isoDate` argument to [Date].
 *
 * @param isoDate character sequence to convert
 *
 * @return Date instance
 */
fun String.toDate() =
        Date.from(
                LocalDateTime.parse(this, FORMATTER).atZone(ZoneId.systemDefault()).toInstant())

fun String.toSimpleDate() =
        Date.from(
                LocalDateTime.parse(this, DATE_FORMAT_PATTERN).atZone(ZoneOffset.UTC).toInstant())


/**
 * Converts `localDate` argument to [Date].

 * @param localDateTime Local datetime to convert
 * *
 * @return Date instance
 */
fun LocalDateTime.asDate() = Date.from(this.atZone(ZoneId.systemDefault()).toInstant())

/**
 * Converts [Date] argument to `isoDate`.

 * @param date Date to format
 * *
 * @return Date instance
 */
fun Date.fromDate() = FORMATTER.format(LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault()))
