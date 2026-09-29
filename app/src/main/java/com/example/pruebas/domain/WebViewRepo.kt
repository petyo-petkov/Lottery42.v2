package com.example.pruebas.domain

import kotlinx.serialization.json.JsonObject

interface WebViewRepo {

    suspend fun getInfoAllGames(url: String): List<JsonObject>

    suspend fun getPremioLNAC(numDecimo: String, idSorteo: String) : String

    suspend fun getPremios(ticket: Ticket): String



}