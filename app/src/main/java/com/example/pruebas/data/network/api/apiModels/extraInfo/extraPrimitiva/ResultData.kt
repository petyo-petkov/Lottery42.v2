package com.example.pruebas.data.network.api.apiModels.extraInfo.extraPrimitiva


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    @SerialName("combinacionRaw")
    val combinacionRaw: String,
    @SerialName("complementario")
    val complementario: Int,
    @SerialName("escrutinioJoker")
    val escrutinioJoker: List<EscrutinioJoker>,
    @SerialName("joker")
    val joker: Joker,
    @SerialName("reintegro")
    val reintegro: Int
)