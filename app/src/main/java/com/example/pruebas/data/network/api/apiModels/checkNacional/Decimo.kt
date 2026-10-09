package com.example.pruebas.data.network.api.apiModels.checkNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Decimo(
    @SerialName("formattedPrize")
    val formattedPrize: String,
    @SerialName("isWinner")
    val isWinner: Boolean,
    @SerialName("number")
    val number: String,
    @SerialName("prize")
    val prize: Int,
    @SerialName("prizePerSerie")
    val prizePerSerie: Int
)