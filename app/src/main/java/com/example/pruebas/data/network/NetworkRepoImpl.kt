package com.example.pruebas.data.network

import android.util.Log
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.Ticket
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import kotlinx.serialization.json.JsonObject

class NetworkRepoImpl(private val client: HttpClient) : NetworkRepo {
    override suspend fun getLatestResult(
        game: String,
        date: String
    ): Result<JsonObject> {
        return try {
            val response = client.get {
                url {
                    path("results", game, "date", date)
                }
            }
            Log.d("NetworkRepoImpl", "getLatestResult URL: ${response.call.request.url}")
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // https://api.loteriasapi.com/api/v1/results/:gameType/check

    override suspend fun checkLottery(ticket: Ticket): List<Result<CheckModel>> {
        return ticket.numbers.map { combination ->
            try {
                val response = client.get {
                    url {
                        path("results", ticket.gameType, "check")
                    }
                    parameter("numbers", combination)
                    ticket.reintegro?.let { parameter("extraNumbers", it) }
                    if (ticket.drawId.isNotEmpty()) {
                        parameter("drawId", ticket.drawId)
                    }
                }
                Log.d("NetworkRepoImpl", "checkLottery URL: ${response.call.request.url}")
                Result.success(response.body())
            } catch (e: Exception) {
                Log.e("NetworkRepoImpl", "Error checking combination: $combination", e)
                Result.failure(e)
            }
        }
    }


}
