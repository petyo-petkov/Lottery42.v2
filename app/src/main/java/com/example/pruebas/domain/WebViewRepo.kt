package com.example.pruebas.domain

import com.example.pruebas.data.network.InfoLNAC
import kotlinx.serialization.json.JsonObject

interface WebViewRepo {

    suspend fun getInfoAllGames(url: String): List<JsonObject>

    suspend fun getInfoLNAC(numSorteo: String, gameId: String) : InfoLNAC

    suspend fun getPremioLNAC(numDecimo: String, idSorteo: String) : String



}