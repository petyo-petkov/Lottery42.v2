package com.example.pruebas.data.network

import android.webkit.WebView
import com.example.pruebas.data.network.lotteryModels.LNAC.ProximosLNAC
import com.example.pruebas.data.network.lotteryModels.LNAC.UltimosLNAC
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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class NetworkRepoImpl(private val client: HttpClient, private val webView: WebView) : NetworkRepo {

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
                Result.success(response.body())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getInfoAllGames(): List<JsonObject> {

        val rawString = fetchData(
            webView = webView,
            url = FUTURE_ALL_GAMES,
            fetchFun = ::getRawString
        )
        return jsonConfig().decodeFromString(rawString)
    }

    override suspend fun getInfoProximosLNAC(): List<ProximosLNAC> {

        val rawString = fetchData(
            webView = webView,
            url = FUTURE_LNAC_GAMES,
            fetchFun = ::getRawString
        )
        return jsonConfig().decodeFromString(rawString)

    }

    override suspend fun getInfoUltimosLNAC(): List<UltimosLNAC> {
        val rawString = fetchData(
            webView = webView,
            url = GET_ULTIMOS_CELEBRADOS_LNAC,
            fetchFun = ::getRawString
        )
        return jsonConfig().decodeFromString(rawString)
    }


}

private fun jsonConfig(): Json {
    return Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
        isLenient = true
        allowSpecialFloatingPointValues = true
        prettyPrint = true
        useArrayPolymorphism = true
        allowStructuredMapKeys = true
    }
}

