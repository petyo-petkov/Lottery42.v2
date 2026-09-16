package com.example.pruebas.domain

import kotlinx.serialization.json.JsonObject

interface NetworkRepo {
    suspend fun getLatestResult(game: String, date: String): Result<JsonObject>
    suspend fun checkLottery(game: String, numbers: List<String>, drawId: String?): JsonObject

}
