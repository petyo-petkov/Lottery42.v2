package com.example.pruebas.data

import android.util.Log
import com.example.pruebas.domain.Ticket
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

private val MESES = mapOf(
    "ENE" to "01", "FEB" to "02", "MAR" to "03", "ABR" to "04",
    "MAY" to "05", "JUN" to "06", "JUL" to "07", "AGO" to "08",
    "SEP" to "09", "OCT" to "10", "NOV" to "11", "DIC" to "12"
)

private val SPANISH_LOCALE = Locale.forLanguageTag("es-ES")

private val INPUT_FORMATTER = DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    .appendOptional(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, HH:mm"))
    .appendOptional(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm"))
    .appendOptional(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy"))
    .appendOptional(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy"))
    .appendOptional(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    .toFormatter(SPANISH_LOCALE)

private val OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy", SPANISH_LOCALE)
private val API_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")

fun String.toStoreDate(): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("ddMMyy")
        val fechaEng = this.replace(Regex("[A-Z]{3}")) { MESES[it.value] ?: it.value }
        val fecha = LocalDate.parse(fechaEng, formatter)
        "$fecha 21:30:00"
    } catch (e: Exception) {
        Log.e("DateTimeConversion", "Error al convertir la fecha (toStoreDate): $e")
        this
    }
}

fun String.toDisplayDate(): String {
    if (this.isBlank()) return this
    return try {
        val temporal = INPUT_FORMATTER.parseBest(
            this.trim(),
            LocalDateTime::from,
            LocalDate::from
        )
        val localDate = when (temporal) {
            is LocalDateTime -> temporal.toLocalDate()
            is LocalDate -> temporal
            else -> return this
        }
        localDate.format(OUTPUT_FORMATTER)
    } catch (e: Exception) {
        Log.e("DateTimeConversion", "Error al convertir la fecha (toDisplayDate): $e")
        this
    }
}

fun String.toApiDateFormat(): String {
    if (this.isBlank()) return ""
    val ymdMatch = Regex("""^(\d{4})-(\d{2})-(\d{2})""").find(this.trim())
    if (ymdMatch != null) {
        val y = ymdMatch.groupValues[1]
        val m = ymdMatch.groupValues[2]
        val d = ymdMatch.groupValues[3]
        return "$y$m$d"
    }
    if (Regex("""^\d{8}$""").matches(this.trim())) {
        return this.trim()
    }
    return try {
        val temporal = INPUT_FORMATTER.parseBest(
            this.trim(),
            LocalDateTime::from,
            LocalDate::from
        )
        val localDate = when (temporal) {
            is LocalDateTime -> temporal.toLocalDate()
            is LocalDate -> temporal
            else -> null
        }
        localDate?.format(API_DATE_FORMATTER) ?: this.replace("-", "").filter { it.isDigit() }.take(8)
    } catch (e: Exception) {
        Log.e("DateTimeConversion", "Error al convertir la fecha (toApiDateFormat): $e")
        this.replace("-", "").filter { it.isDigit() }.take(8)
    }
}

fun parseClosureDateTime(cierreStr: String?): LocalDateTime? {
    if (cierreStr.isNullOrBlank()) return null
    val cleanStr = cierreStr.trim()
    return try {
        val regexYmdHms = Regex("""^(\d{4})[-/]?(\d{2})[-/]?(\d{2})[T\s](\d{2}):(\d{2})(?::(\d{2}))?""").find(cleanStr)
        if (regexYmdHms != null) {
            val y = regexYmdHms.groupValues[1].toInt()
            val m = regexYmdHms.groupValues[2].toInt()
            val d = regexYmdHms.groupValues[3].toInt()
            val hh = regexYmdHms.groupValues[4].toInt()
            val mm = regexYmdHms.groupValues[5].toInt()
            val ss = regexYmdHms.groupValues[6].toIntOrNull() ?: 0
            LocalDateTime.of(y, m, d, hh, mm, ss)
        } else if (Regex("""^\d{14}$""").matches(cleanStr)) {
            val y = cleanStr.substring(0, 4).toInt()
            val m = cleanStr.substring(4, 6).toInt()
            val d = cleanStr.substring(6, 8).toInt()
            val hh = cleanStr.substring(8, 10).toInt()
            val mm = cleanStr.substring(10, 12).toInt()
            val ss = cleanStr.substring(12, 14).toInt()
            LocalDateTime.of(y, m, d, hh, mm, ss)
        } else {
            val date = parseDrawDate(cleanStr)
            date?.atTime(20, 30)
        }
    } catch (e: Exception) {
        null
    }
}

fun parseDrawDate(dateStr: String?): LocalDate? {
    if (dateStr.isNullOrBlank()) return null
    val apiDate = dateStr.toApiDateFormat()
    if (apiDate.length == 8) {
        return try {
            val y = apiDate.substring(0, 4).toInt()
            val m = apiDate.substring(4, 6).toInt()
            val d = apiDate.substring(6, 8).toInt()
            LocalDate.of(y, m, d)
        } catch (e: Exception) {
            null
        }
    }
    return null
}

fun isDrawCelebrated(
    ticket: Ticket,
    cierreOverride: String? = null,
    estadoOverride: String? = null
): Boolean {
    val now = LocalDateTime.now()

    val cierreToUse = cierreOverride ?: ticket.cierre
    if (!cierreToUse.isNullOrBlank()) {
        parseClosureDateTime(cierreToUse)?.let { closureDateTime ->
            return !now.isBefore(closureDateTime)
        }
    }

    if (!ticket.apertura.isNullOrBlank()) {
        parseClosureDateTime(ticket.apertura)?.let { openingDateTime ->
            if (now.isBefore(openingDateTime)) return false
        }
    }

    if (!estadoOverride.isNullOrBlank()) {
        if (estadoOverride.equals("CELEBRADO", ignoreCase = true)) return true
        if (estadoOverride.equals("PENDIENTE", ignoreCase = true) || estadoOverride.equals("PROXIMO", ignoreCase = true)) return false
    }

    val drawDateTime = parseClosureDateTime(ticket.fecha) ?: return true
    return !now.isBefore(drawDateTime)
}

