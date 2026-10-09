package com.example.pruebas.data.network.api.apiModels.checkNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNacional(
    @SerialName("data")
    val `data`: Data,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timestamp")
    val timestamp: String
)