package com.example.pruebas.data.network

import android.util.Log
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.Ticket
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

class NetworkRepoImpl(private val client: HttpClient) : NetworkRepo {

    override suspend fun getInfo(
       ticket: Ticket
    ): Result<InfoModel> {
        return try {
            val response = client.get {
                url {
                    path("results", ticket.gameType, "date", ticket.drawDate)
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
                    when (ticket.gameType) {
                        "bonoloto" -> {
                            parameter("numbers", combination)
                            ticket.reintegro?.let { parameter("extraNumbers", it) }
                            if (ticket.drawId.isNotEmpty()) {
                                parameter("drawId", ticket.drawId)
                            }
                        }

                        "primitiva" -> {
                            parameter("numbers", combination)
                            ticket.reintegro?.let { parameter("extraNumbers", it) }
                            if (ticket.drawId.isNotEmpty()) {
                                parameter("drawId", ticket.drawId)
                            }

                        }
                        "euromillones" -> {
                            parameter("numbers", combination)
                            parameter("extraNumbers", ticket.stars?.joinToString(","))
                            if (ticket.drawId.isNotEmpty()) {
                                parameter("drawId", ticket.drawId)
                            }

                        }
                        "gordo" -> {
                            parameter("numbers", combination)
                            parameter("extraNumbers", ticket.clave)
                            if (ticket.drawId.isNotEmpty()) {
                                parameter("drawId", ticket.drawId)
                            }
                        }
                        "eromdreams" -> {
                            parameter("numbers", combination)
                            parameter("extraNumbers", ticket.dreams?.joinToString(","))
                            if (ticket.drawId.isNotEmpty()) {
                                parameter("drawId", ticket.drawId)
                            }

                        }
                        "nacional" -> {
                            parameter("numbers", ticket.numLottery)
                            if (ticket.drawId.isNotEmpty()) {
                                parameter("drawId", ticket.drawId)
                            }
                        }
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
