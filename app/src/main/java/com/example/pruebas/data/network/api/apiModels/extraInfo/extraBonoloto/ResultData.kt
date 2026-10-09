package com.example.pruebas.data.network.api.apiModels.extraInfo.extraBonoloto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    @SerialName("combinacionRaw")
    val combinacionRaw: String,
    @SerialName("complementario")
    val complementario: Int,
    @SerialName("reintegro")
    val reintegro: Int
)