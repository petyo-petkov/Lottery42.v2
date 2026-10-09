package com.example.pruebas.data.network.api.apiModels.extraInfo.extraGordo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExtraGordo(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timestamp")
    val timestamp: String
)