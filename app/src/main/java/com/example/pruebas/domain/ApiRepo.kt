package com.example.pruebas.domain

import ExtraInfoSorteos
import com.example.pruebas.data.network.api.apiModels.MissingDataNacional
import kotlinx.serialization.json.JsonObject

interface ApiRepo {

    suspend fun getInfo(url: String): List<JsonObject>

    suspend fun getMissingDataNacional(numSorteo: String): MissingDataNacional

    suspend fun getExtraInfoSorteos(ticket: Ticket): ExtraInfoSorteos?

}

