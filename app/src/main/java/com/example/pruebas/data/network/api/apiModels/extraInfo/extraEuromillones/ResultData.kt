package com.example.pruebas.data.network.api.apiModels.extraInfo.extraEuromillones


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    @SerialName("combinacionRaw")
    val combinacionRaw: String,
    @SerialName("escrutinioMillon")
    val escrutinioMillon: List<EscrutinioMillon>,
    @SerialName("estrellas")
    val estrellas: List<Int>,
    @SerialName("millon")
    val millon: Millon
)