package com.example.pruebas.data.network

import android.util.Log
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import com.example.pruebas.domain.LotteryGame
import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.Ticket
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

class NetworkRepoImpl(private val client: HttpClient) : NetworkRepo {

    override suspend fun getInfo(ticket: Ticket): Result<InfoModel> {
        return try {
            val response = client.get {
                url {
                    path("results", ticket.gameType, "date", ticket.drawDate)
                }
            }
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkLottery(ticket: Ticket): List<Result<CheckModel>> {
        val game = ticket.lotteryGame
        
        // Si no hay combinaciones (ej. Nacional), simulamos una lista con una entrada vacía para hacer la petición
        val combinations = ticket.numbers.ifEmpty { listOf("") }

        return combinations.map { combination ->
            try {
                val response = client.get {
                    url {
                        path("results", ticket.gameType, "check")
                    }
                    
                    if (ticket.drawId.isNotEmpty()) {
                        parameter("drawId", ticket.drawId)
                    }

                    when (game) {
                        is LotteryGame.Bonoloto, is LotteryGame.Primitiva -> {
                            parameter("numbers", combination)
                            ticket.reintegro?.let { parameter("extraNumbers", it) }
                        }
                        is LotteryGame.Euromillones -> {
                            parameter("numbers", combination)
                            parameter("extraNumbers", ticket.stars?.joinToString(","))
                        }
                        is LotteryGame.Gordo -> {
                            parameter("numbers", combination)
                            parameter("extraNumbers", ticket.clave?.joinToString(","))
                        }
                        is LotteryGame.Eurodreams -> {
                            parameter("numbers", combination)
                            parameter("extraNumbers", ticket.dreams?.joinToString(","))
                        }
                        is LotteryGame.Nacional -> {
                            parameter("numbers", ticket.numLottery)
                        }
                        else -> {}
                    }
                }
                Result.success(response.body())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
