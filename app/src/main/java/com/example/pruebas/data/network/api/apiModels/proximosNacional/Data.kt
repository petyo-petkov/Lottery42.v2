package com.example.pruebas.data.network.api.apiModels.proximosNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("closingDate")
    val closingDate: String,
    @SerialName("dayOfWeek")
    val dayOfWeek: String,
    @SerialName("drawDate")
    val drawDate: String,
    @SerialName("drawId")
    val drawId: String,
    @SerialName("game")
    val game: Game,
    @SerialName("id")
    val id: String,
    @SerialName("metadata")
    val metadata: Metadata,
    @SerialName("openingDate")
    val openingDate: String,
    @SerialName("status")
    val status: String,
    @SerialName("year")
    val year: Int
)