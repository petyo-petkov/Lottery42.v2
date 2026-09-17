package com.example.pruebas.data

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val MESES = mapOf(
    "ENE" to "01", "FEB" to "02", "MAR" to "03", "ABR" to "04",
    "MAY" to "05", "JUN" to "06", "JUL" to "07", "AGO" to "08",
    "SEP" to "09", "OCT" to "10", "NOV" to "11", "DIC" to "12"
)
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
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val localDate = LocalDate.parse(this, inputFormatter)
        outputFormatter.format(localDate)
    } catch (e: Exception) {
        Log.e("DateTimeConversion", "Error al convertir la fecha (toDisplayDate): $e")
        this
    }
}



