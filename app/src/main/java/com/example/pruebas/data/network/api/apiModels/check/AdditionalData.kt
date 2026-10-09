package com.example.pruebas.data.network.api.apiModels.check


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionalData(
    @SerialName("elMillon")
    val elMillon: String
)