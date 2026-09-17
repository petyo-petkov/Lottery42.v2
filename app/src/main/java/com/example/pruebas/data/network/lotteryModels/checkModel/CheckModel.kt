package com.example.pruebas.data.network.lotteryModels.checkModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckModel(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("timestamp")
    val timestamp: String? = null
)