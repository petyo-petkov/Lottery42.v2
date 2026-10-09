package com.example.pruebas.data.network.api.apiModels.extraInfo.extraEurodreams


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExtraEurodreams(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timestamp")
    val timestamp: String
)