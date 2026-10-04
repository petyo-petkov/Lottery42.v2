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

    // Para getMissingInfoLNAC
    private fun JsonObject.matchesNumSorteo(targetNumSorteo: String): Boolean {
        val targetInt = targetNumSorteo.toIntOrNull()

        val numStr = getString("num_sorteo") ?: getString("numSorteo")
        if (numStr != null) {
            if (numStr == targetNumSorteo) return true
            if (targetInt != null && numStr.toIntOrNull() == targetInt) return true
        }

        val idStr = getString("id_sorteo") ?: getString("drawId")
        if (idStr != null) {
            val last3 = idStr.takeLast(3)
            if (last3 == targetNumSorteo) return true
            if (targetInt != null && last3.toIntOrNull() == targetInt) return true
        }

        return false
    }

    // Para TicketFromBarCode
    override suspend fun getMissingInfoLNAC(numSorteo: String): MissingInfoLNAC? {
        try {
            val proximosLNAC = getInfoAllGames(GET_PROXIMOS_LNAC)
            val proximoSorteo = proximosLNAC.find { it.matchesNumSorteo(numSorteo) }
            Log.i("PROXIMO", proximoSorteo.toString())

            if (proximoSorteo != null) {
                return MissingInfoLNAC(
                    drawId = proximoSorteo.getString("id_sorteo") ?: proximoSorteo.getString("drawId") ?: "",
                    drawDate = proximoSorteo.getString("fecha") ?: proximoSorteo.getString("fecha_sorteo") ?: "",
                    precio = proximoSorteo.getString("precio") ?: proximoSorteo.getString("precioDecimo") ?: "0.0",
                    gameStatus = proximoSorteo.getString("estado") ?: "",
                    isCelebrado = false
                )
            }
        } catch (e: Exception) {
            Log.e("WebViewRepoImpl", "Error obtener missingInfo proximos", e)
        }

        try {
            val ultimosLNAC = getInfoAllGames(GET_ULTIMOS_CELEBRADOS_LNAC)
            val ultimoSorteo = ultimosLNAC.find { it.matchesNumSorteo(numSorteo) }
            Log.i("ULTIMO", ultimoSorteo.toString())

            if (ultimoSorteo != null) {
                return MissingInfoLNAC(
                    drawId = ultimoSorteo.getString("id_sorteo") ?: ultimoSorteo.getString("drawId") ?: "",
                    drawDate = ultimoSorteo.getString("fecha_sorteo") ?: ultimoSorteo.getString("fecha") ?: "",
                    precio = ultimoSorteo.getString("precioDecimo") ?: ultimoSorteo.getString("precio") ?: "0.0",
                    gameStatus = ultimoSorteo.getString("estado") ?: "",
                    isCelebrado = true
                )
            }

        } catch (e: Exception) {
            Log.e("WebViewRepoImpl", "Error obtener missingInfo ultimos", e)
        }

        try {
            val urlBuscador = urlUltimosTresMeses("LNAC")
            val sorteos3Meses = getInfoAllGames(urlBuscador)
            val sorteoMatch = sorteos3Meses.find { it.matchesNumSorteo(numSorteo) }
            Log.i("BUSCADOR_3_MESES", sorteoMatch.toString())

            if (sorteoMatch != null) {
                val gameStatus = sorteoMatch.getString("estado") ?: ""
                val isCelebrado = gameStatus.equals("CELEBRADO", ignoreCase = true) || gameStatus.equals("COMPLETED", ignoreCase = true)
                return MissingInfoLNAC(
                    drawId = sorteoMatch.getString("id_sorteo") ?: sorteoMatch.getString("drawId") ?: "",
                    drawDate = sorteoMatch.getString("fecha_sorteo") ?: sorteoMatch.getString("fecha") ?: "",
                    precio = sorteoMatch.getString("precioDecimo") ?: sorteoMatch.getString("precio") ?: "0.0",
                    gameStatus = gameStatus,
                    isCelebrado = isCelebrado
                )
            }
        } catch (e: Exception) {
            Log.e("WebViewRepoImpl", "Error obtener missingInfo buscador 3 meses", e)
        }

        return null
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
        val gameId = when (ticket.gameType) {
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

@Serializable
data class MissingInfoLNAC(
    val drawId: String,
    val drawDate: String,
    val precio: String,
    val gameStatus: String,
    val isCelebrado: Boolean
)