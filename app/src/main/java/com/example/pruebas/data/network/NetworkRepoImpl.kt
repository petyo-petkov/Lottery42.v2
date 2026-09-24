package com.example.pruebas.data.network

import android.util.Log
import android.webkit.WebView
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
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlin.toString

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

    override suspend fun getInfoAllGames(url: String): List<JsonObject> {
        val rawString = fetchData(
            webView = webView,
            url = url,
            fetchFun = ::getRawString
        )
        return jsonConfig().decodeFromString(rawString)
    }

    override suspend fun getInfoLNAC(numSorteo: String, gameId: String): InfoLNAC {
        val urlProximos = GET_PROXIMOS_LNAC
        val urlUltimos = GET_ULTIMOS_CELEBRADOS_LNAC

        val proximos = findSorteo(urlProximos, gameId, numSorteo)
        val ultimos = findSorteo(urlUltimos, gameId, numSorteo)

        Log.i("proximos", proximos.toString())
        Log.i("ultimos", ultimos.toString())

        return try {
            when {
                !ultimos.isNullOrEmpty() -> getMissingInfoL(ultimos)
                !proximos.isNullOrEmpty() -> getMissingInfoL(proximos)
                else -> InfoLNAC()
            }
        } catch (e: Exception) {
            Log.e("ERROR getInfoSorteo", e.message.toString())
            InfoLNAC()
        }
    }

    private suspend fun findSorteo(url: String, gameID: String, numSorteo: String): JsonObject? {
        fun JsonObject.getString(key: String): String? {
            val element = this[key]
            return if (element is JsonPrimitive) element.contentOrNull else null
        }

        return getInfoAllGames(url).find { item ->
            val gameIdMatches = item.getString("game_id")?.equals(gameID, ignoreCase = true) == true
            if (!gameIdMatches) return@find false

            val idSorteo = item.getString("id_sorteo") ?: ""
            val numSorteoField = item.getString("num_sorteo") ?: ""
            val cdc = item.getString("cdc") ?: ""

            val numSorteoInt = numSorteo.toIntOrNull()

            numSorteoField == numSorteo ||
                    cdc == numSorteo ||
                    (idSorteo.length >= 3 && idSorteo.takeLast(3) == numSorteo) ||
                    (numSorteoInt != null && (numSorteoField.toIntOrNull() == numSorteoInt || cdc.toIntOrNull() == numSorteoInt))
        }
    }

    private fun getMissingInfoL(info: JsonObject): InfoLNAC {
        fun JsonObject.getString(key: String): String? {
            val element = this[key]
            return if (element is JsonPrimitive) element.contentOrNull else null
        }
        return InfoLNAC(
            fecha = info.getString("fecha") ?: info.getString("fecha_sorteo") ?: "",
            precio = info.getString("precio") ?: info.getString("precioDecimo") ?: "0.0",
            idSorteo = info.getString("id_sorteo") ?: "",
            apertura = info.getString("apertura") ?: info.getString("fecha_sorteo") ?: "",
            cierre = info.getString("cierre") ?: info.getString("fecha_sorteo") ?: ""
        )
    }


}

@Serializable
data class InfoLNAC(
    val precio: String? = null,
    val idSorteo: String? = "",
    val fecha: String? = "",
    val apertura: String? = "",
    val cierre: String? = ""
)


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

