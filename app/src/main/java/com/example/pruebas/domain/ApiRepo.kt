package com.example.pruebas.domain

import com.example.pruebas.data.network.api.apiModels.MissingDataNacional
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraBonoloto.ExtraBonoloto
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraEurodreams.ExtraEurodreams
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraEuromillones.ExtraEuromillones
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraGordo.ExtraGordo
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraNacional.ExtraNacional
import com.example.pruebas.data.network.api.apiModels.extraInfo.extraPrimitiva.ExtraPrimitiva
import kotlinx.serialization.json.JsonObject

interface ApiRepo {

    suspend fun getInfo(url: String): List<JsonObject>

    suspend fun getMissingDataNacional(numSorteo: String): MissingDataNacional

    suspend fun getExtraPrimitiva(ticket: Ticket): ExtraPrimitiva?

    suspend fun getExtraBonoloto(ticket: Ticket): ExtraBonoloto?

    suspend fun getExtraEuromillones(ticket: Ticket): ExtraEuromillones?

    suspend fun getExtraEurodreams(ticket: Ticket): ExtraEurodreams?

    suspend fun getExtraGordo(ticket: Ticket): ExtraGordo?

    suspend fun getExtraNacional(ticket: Ticket): ExtraNacional?

}

