package com.example.pruebas.data.network.lotteryModels.proximosNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProximosNacional(
    @SerialName("data")
    val `data`: List<Data?>?,
    @SerialName("success")
    val success: Boolean?,
    @SerialName("timestamp")
    val timestamp: String?
)