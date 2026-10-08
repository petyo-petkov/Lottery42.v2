package com.example.pruebas.data.network.api.apiModels.ultimosNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("combination")
    val combination: List<String?> = emptyList(),
    @SerialName("dayOfWeek")
    val dayOfWeek: String,
    @SerialName("documents")
    val documents: List<Document>,
    @SerialName("drawDate")
    val drawDate: String,
    @SerialName("drawId")
    val drawId: String,
    @SerialName("game")
    val game: Game,
    @SerialName("id")
    val id: String,
    @SerialName("officialListUrl")
    val officialListUrl: String? = null,
    @SerialName("prizes")
    val prizes: List<Prize>,
    @SerialName("resultData")
    val resultData: ResultData,
    @SerialName("status")
    val status: String,
    @SerialName("year")
    val year: Int
)