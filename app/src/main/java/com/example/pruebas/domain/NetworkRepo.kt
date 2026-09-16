package com.example.pruebas.domain

import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import kotlinx.serialization.json.JsonObject

interface NetworkRepo {
    suspend fun getLatestResult(game: String, date: String): Result<JsonObject>
    suspend fun checkLottery(
        game: String,
        numbers: List<String>,
        extraNumbers: List<String>?,
        drawId: String?
    ): CheckModel

}
