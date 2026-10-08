package com.example.pruebas.data.network.api.apiModels.ultimosNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UltimosNacional(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("meta")
    val meta: Meta,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timestamp")
    val timestamp: String
)