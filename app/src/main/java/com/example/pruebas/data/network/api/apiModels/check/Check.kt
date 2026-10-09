package com.example.pruebas.data.network.api.apiModels.check


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Check(
    @SerialName("data")
    val `data`: Data,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timestamp")
    val timestamp: String
)