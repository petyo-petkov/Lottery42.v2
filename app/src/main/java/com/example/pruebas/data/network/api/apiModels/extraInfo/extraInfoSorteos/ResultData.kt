package com.example.pruebas.data.network.api.apiModels.extraInfo.extraInfoSorteos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    @SerialName("combinacionRaw")
    val combinacionRaw: String,
    @SerialName("complementario")
    val complementario: Int? = null,
    @SerialName("escrutinioJoker")
    val escrutinioJoker: List<EscrutinioJoker>? = emptyList(),
    @SerialName("joker")
    val joker: Joker? = null,
    @SerialName("reintegro")
    val reintegro: Int? = null,

    @SerialName("escrutinioMillon")
    val escrutinioMillon: List<EscrutinioMillon>? = emptyList(),
    @SerialName("estrellas")
    val estrellas: List<Int>? = emptyList(),
    @SerialName("millon")
    val millon: Millon? = null,

    @SerialName("sueno")
    val sueno: Int? = null
)
