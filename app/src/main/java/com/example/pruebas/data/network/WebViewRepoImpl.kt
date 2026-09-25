package com.example.pruebas.data.network

import android.util.Log
import android.webkit.WebView
import com.example.pruebas.domain.WebViewRepo
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull

class WebViewRepoImpl(private val webView: WebView) : WebViewRepo {

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

    override suspend fun getPremioLNAC(numDecimo: String, idSorteo: String): String {

        fun JsonObject.getString(key: String): String? {
            val element = this[key]
            return if (element is JsonPrimitive) element.contentOrNull else null
        }

        val url = urlPremioLNACPorNumero(numeroLoteria = numDecimo, idSorteo = idSorteo)

        val data = getInfoAllGames(url)

        return data[0].getString("premioEnCentimos") ?: "0.2"

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