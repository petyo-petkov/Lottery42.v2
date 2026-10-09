package com.example.pruebas.data

import java.text.NumberFormat
import java.util.Locale


fun Number.toCurrencyFormat(
    locale: Locale = Locale("es", "ES"),
    withDecimals: Boolean = true
): String {
    val formatter = NumberFormat.getCurrencyInstance(locale).apply {
        if (!withDecimals) {
            maximumFractionDigits = 0
        }
    }
    return formatter.format(this)
}


fun String?.parsePrize(): Double {
    if (this.isNullOrBlank()) return 0.0
    val cleaned = this.replace("€", "").trim()
    val normalized = if (cleaned.contains(".") && cleaned.contains(",")) {
        cleaned.replace(".", "").replace(",", ".")
    } else if (cleaned.contains(",")) {
        cleaned.replace(",", ".")
    } else {
        cleaned
    }
    return normalized.toDoubleOrNull() ?: 0.0
}

fun String.toCurrencyFormat(
    locale: Locale = Locale("es", "ES"),
    withDecimals: Boolean = true
): String {
    val numericValue = this.parsePrize()
    return numericValue.toCurrencyFormat(locale, withDecimals)
}