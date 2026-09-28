package com.example.pruebas.data.network

import android.util.Log
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import com.example.pruebas.data.network.lotteryModels.infoNacional.InfoNacional
import com.example.pruebas.domain.LotteryGame
import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.Ticket
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.request

class NetworkRepoImpl(private val client: HttpClient) : NetworkRepo {

    override suspend fun getInfo(ticket: Ticket): Result<InfoModel> {
        return try {
            val response = client.get("results/${ticket.gameType}/date/${ticket.drawDate}")
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getInfoNacional(numSorteo: String): InfoNacional {
        val response = client.get("results/nacional").body<InfoNacional>()
        val sorteo = response.data?.find {
            it?.drawId?.takeLast(3) == numSorteo
        }
        return response.copy(data = listOf(sorteo))

    }

    override suspend fun checkLottery(ticket: Ticket): List<Result<CheckModel>> {

        val game = ticket.lotteryGame
        val combinations = ticket.numbers.ifEmpty { listOf("") }

        return combinations.map { combination ->
            try {
                val response = client.get("results/${ticket.gameType}/check") {
                    if (ticket.drawId.isNotEmpty()) {
                        parameter("drawId", ticket.drawId)
                    }

                    when (game) {
                        is LotteryGame.Bonoloto, is LotteryGame.Primitiva -> {
                            parameter("numbers", "$combination,${ticket.reintegro}")
                            // ticket.reintegro?.let { parameter("extraNumbers", it) }
                        }

                        is LotteryGame.Euromillones -> {
                            parameter("numbers", combination)
                            parameter("extraNumbers", ticket.stars?.joinToString(","))
                        }

                        is LotteryGame.Gordo -> {
                            parameter("numbers", "$combination,${ticket.clave}")
                            //parameter("extraNumbers", ticket.clave?.joinToString(","))
                        }

                        is LotteryGame.Eurodreams -> {
                            parameter("numbers", combination)
                            parameter("extraNumbers", ticket.dreams?.joinToString(","))
                        }

                        is LotteryGame.Nacional -> {
                            parameter("numbers", ticket.numDecimo)
                        }

                        else -> {}
                    }
                }
                Log.i("CheckURL:", response.request.url.toString())
                Result.success(response.body())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


}