package com.example.pruebas.data.network.api.apiModels.extraInfo.extraGordo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    @SerialName("combinacionRaw")
    val combinacionRaw: String,
    @SerialName("reintegro")
    val reintegro: Int
)