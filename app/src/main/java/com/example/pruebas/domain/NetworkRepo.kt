package com.example.pruebas.domain

import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import kotlinx.serialization.json.JsonObject

interface NetworkRepo {
    suspend fun getLatestResult(game: String, date: String): Result<JsonObject>
    suspend fun checkLottery(ticket: Ticket): List<Result<CheckModel>>
    //suspend fun checkLottery(ticket: Ticket): Result<CheckModel>

}
