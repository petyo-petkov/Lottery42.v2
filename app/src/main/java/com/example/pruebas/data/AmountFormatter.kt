package com.example.pruebas.data

import java.text.NumberFormat
import java.util.Locale

fun Double.toMoneyFormat(): String {
    //val value = this.toDoubleOrNull() ?: 0.0
    // Si tu número original 2345432 representa céntimos (23454,32), divídelo por 100.0:
    // val realValue = value / 100.0

    val esLocale = Locale("es", "ES")
    val formatter = NumberFormat.getNumberInstance(esLocale ).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return formatter.format(this) // "2.345.432,00" o si divides entre 100: "23.454,32"
}