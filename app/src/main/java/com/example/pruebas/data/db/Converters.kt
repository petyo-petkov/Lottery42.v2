package com.example.pruebas.data.db

import androidx.room3.ColumnTypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @ColumnTypeConverter
    fun fromList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @ColumnTypeConverter
    fun toList(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}
