package com.example.pruebas.domain

import com.example.pruebas.data.network.MissingInfoLNAC
import kotlinx.serialization.json.JsonObject

interface WebViewRepo {

    suspend fun getInfoAllGames(url: String): List<JsonObject>

    suspend fun getMissingInfoLNAC(numSorteo: String): MissingInfoLNAC?

    suspend fun getPremioLNAC(numDecimo: String, idSorteo: String): String

    suspend fun getPremios(ticket: Ticket): String

    suspend fun getJackpot(): List<JsonObject>

}
