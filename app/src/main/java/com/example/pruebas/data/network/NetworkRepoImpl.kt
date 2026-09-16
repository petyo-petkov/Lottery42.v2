package com.example.pruebas.data.network

import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.domain.NetworkRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.JsonObject

class NetworkRepoImpl(private val client: HttpClient) : NetworkRepo {
    override suspend fun getLatestResult(
        game: String,
        date: String
    ): Result<JsonObject> {
        val urlString = "results/$game/date/$date"
        return try {
            val response: JsonObject = client.get(urlString).body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkLottery(
        game: String,
        numbers: List<String>,        // Ejemplo: 5,14,22,31,38,45
        extraNumbers: List<String>?,  // Ejemplo: 1,2
        drawId: String?
    ): CheckModel {
       // val formattedNumbers = numbers.joinToString(",").replace(" ", ",")
        return client.get("results/$game/check") {
            parameter("numbers", numbers)
            parameter("extraNumbers", extraNumbers)
            parameter("drawId", drawId)
        }.body()
    }


}

