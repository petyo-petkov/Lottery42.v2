package com.example.pruebas.data.network.lotteryModels.infoModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    @SerialName("complementario")
    val complementario: Int? = null,
    @SerialName("reintegro")
    val reintegro: Int? = null
)