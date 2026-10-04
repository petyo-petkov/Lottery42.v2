package com.example.pruebas.data

import java.text.NumberFormat
import java.util.Locale


fun Number.toCurrencyFormat(
    locale: Locale = Locale("es", "ES"),
    withDecimals: Boolean = false
): String {
    val formatter = NumberFormat.getCurrencyInstance(locale).apply {
        if (!withDecimals) {
            maximumFractionDigits = 0
        }
    }
    return formatter.format(this)
}


fun String.toCurrencyFormat(locale: Locale = Locale("es", "ES")): String {
    val numericValue = this.toDoubleOrNull() ?: return this
    return numericValue.toCurrencyFormat(locale)
}