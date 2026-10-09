package com.example.pruebas.data.network.api

import android.util.Log
import com.example.pruebas.data.network.api.apiModels.GET_PROXIMOS_NACIONAL
import com.example.pruebas.data.network.api.apiModels.GET_ULTIMOS_NACIONAL
import com.example.pruebas.data.network.api.apiModels.MissingDataNacional
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraBonoloto.ExtraBonoloto
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraEurodreams.ExtraEurodreams
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraEuromillones.ExtraEuromillones
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraGordo.ExtraGordo
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraNacional.ExtraNacional
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraPrimitiva.ExtraPrimitiva
import com.example.pruebas.data.network.api.apiModels.getExtra
import com.example.pruebas.data.network.api.apiModels.proximosNacional.ProximosNacional
import com.example.pruebas.data.network.api.apiModels.ultimosNacional.UltimosNacional
import com.example.pruebas.domain.ApiRepo
import com.example.pruebas.domain.Ticket
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.JsonObject


class ApiRepoImpl(private val client: HttpClient) : ApiRepo {

    private suspend inline fun <reified T> fetchExtraData(ticket: Ticket, tag: String): T? {
        return try {
            val url = getExtra(gameType = ticket.gameId, date = ticket.fecha)
            client.get(url).body<T>()
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
            null
        }
    }

    override suspend fun getInfo(url: String): List<JsonObject> {

        return client.get(url).body<List<JsonObject>>()

    }

    override suspend fun getMissingDataNacional(numSorteo: String): MissingDataNacional {

        try {
            val proximos = client
                .get(GET_PROXIMOS_NACIONAL)
                .body<ProximosNacional>()

            val sorteo = proximos.data.find { it.drawId.takeLast(3) == numSorteo }

            Log.i("proximo sorteo", sorteo.toString())

            if (sorteo != null) {
                return MissingDataNacional(
                    drawId = sorteo.drawId,
                    fecha = sorteo.drawDate,
                    cierre = sorteo.closingDate,
                    precio = sorteo.metadata.precio.toString(),
                    status = sorteo.status
                )
            }
        } catch (e: Exception) {
            Log.e("Error en getMissingData proximos", e.message ?: "")
        }

        try {
            val ultimos = client
                .get(GET_ULTIMOS_NACIONAL)
                .body<UltimosNacional>()

            val sorteo = ultimos.data.find { it.drawId.takeLast(3) == numSorteo }

            Log.i("ultimo sorteo", sorteo.toString())

            if (sorteo != null) {
                val precio = sorteo.resultData.reintegros.firstOrNull()?.prize?.div(100)?.toString() ?: "0.0"
                return MissingDataNacional(
                    drawId = sorteo.drawId,
                    fecha = sorteo.drawDate,
                    cierre = sorteo.drawDate,
                    precio = precio,
                    status = sorteo.status
                )
            }
        } catch (e: Exception) {
            Log.e("Error en getMissingData ultimos", e.message ?: "")
        }

        return MissingDataNacional("", "", "", "", "")
    }


    override suspend fun getExtraPrimitiva(ticket: Ticket): ExtraPrimitiva? =
        fetchExtraData(ticket, "Error extraPrimitiva")

    override suspend fun getExtraBonoloto(ticket: Ticket): ExtraBonoloto? =
        fetchExtraData(ticket, "Error extraBonoloto")

    override suspend fun getExtraEuromillones(ticket: Ticket): ExtraEuromillones? =
        fetchExtraData(ticket, "Error extraMillones")

    override suspend fun getExtraEurodreams(ticket: Ticket): ExtraEurodreams? =
        fetchExtraData(ticket, "Error extraEurodreams")

    override suspend fun getExtraGordo(ticket: Ticket): ExtraGordo? =
        fetchExtraData(ticket, "Error extraGordo")

    override suspend fun getExtraNacional(ticket: Ticket): ExtraNacional? =
        fetchExtraData(ticket, "Error extraNacional")


}
