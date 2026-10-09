package com.example.pruebas.data.network.api.apiModels.extraInfo.extraPrimitiva


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Statistics(
    @SerialName("prizePool")
    val prizePool: String
)