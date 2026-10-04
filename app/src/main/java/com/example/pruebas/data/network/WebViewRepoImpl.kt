package com.example.pruebas.data.network

import android.util.Log
import android.webkit.WebView
import com.example.pruebas.data.network.urlsGetPremio.urlPremioBONO
import com.example.pruebas.data.network.urlsGetPremio.urlPremioEDMS
import com.example.pruebas.data.network.urlsGetPremio.urlPremioELGR
import com.example.pruebas.data.network.urlsGetPremio.urlPremioEMIL
import com.example.pruebas.data.network.urlsGetPremio.urlPremioLAPR
import com.example.pruebas.domain.Ticket
import com.example.pruebas.domain.WebViewRepo
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







    override suspend fun getJackpot(): List<JsonObject> {
        val jackpot = fetchData(
            webView = webView,
            url = GET_JACKPOT,
            fetchFun = ::getRawString
        )
        if (jackpot.startsWith("Error:")) {
            throw Exception(jackpot)
        }
        return jsonConfig().decodeFromString(jackpot)
    }

    override suspend fun getPremios(ticket: Ticket): String {
        val url = when (ticket.gameType) {
            "primitiva" -> urlPremioLAPR(ticket)
            "bonoloto" -> urlPremioBONO(ticket)
            "euromillones" -> urlPremioEMIL(ticket)
            "eurodreams" -> urlPremioEDMS(ticket)
            "gordo" -> urlPremioELGR(ticket)
            else -> ""
        }
        val gameId = when(ticket.gameType) {
            "bonoloto" -> "BONO"
            "primitiva" -> "LAPR"
            "euromillones" -> "EMIL"
            "eurodreams" -> "EDMS"
            "gordo" -> "ELGR"
            else -> ""
        }
        Log.i("URL", url)
        val premio = fetchData(webView, url) { getPremio(gameId) }
        return premio
    }

    override suspend fun getPremioLNAC(numDecimo: String, idSorteo: String): String {

        val url = urlPremioLNACPorNumero(numeroLoteria = numDecimo, idSorteo = idSorteo)
        val data = getInfoAllGames(url)
        return data[0].getString("premioEnCentimos") ?: "0.2"
    }
    private fun JsonObject.getString(key: String): String? {
        val element = this[key]
        return if (element is JsonPrimitive) element.contentOrNull else null
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