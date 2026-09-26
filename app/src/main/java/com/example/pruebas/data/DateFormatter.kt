package com.example.pruebas.data

import android.util.Log
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

fun String.toStoreDate(): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("ddMMyy")
        val fechaEng = this.replace(Regex("[A-Z]{3}")) { MESES[it.value] ?: it.value }
        val fecha = LocalDate.parse(fechaEng, formatter)
        fecha.toString()
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
