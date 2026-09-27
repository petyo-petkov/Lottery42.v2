package com.example.pruebas.data.network.lotteryModels.infoNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoNacional(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("success")
    val success: Boolean?,
    @SerialName("timestamp")
    val timestamp: String?
)