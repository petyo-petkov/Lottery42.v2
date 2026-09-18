package com.example.pruebas.data.network.lotteryModels.infoModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoModel(
    @SerialName("data")
    val `data`: List<InfoLotteryData?>? = null,
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("timestamp")
    val timestamp: String? = null
)