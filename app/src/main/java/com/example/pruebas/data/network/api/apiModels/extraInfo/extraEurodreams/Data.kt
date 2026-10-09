package com.example.pruebas.data.network.api.apiModels.extraInfo.extraEurodreams


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("combination")
    val combination: List<Int>,
    @SerialName("dayOfWeek")
    val dayOfWeek: String,
    @SerialName("documents")
    val documents: List<String?> = emptyList(),
    @SerialName("drawDate")
    val drawDate: String,
    @SerialName("drawId")
    val drawId: String,
    @SerialName("game")
    val game: Game,
    @SerialName("id")
    val id: String,
    @SerialName("prizes")
    val prizes: List<Prize>,
    @SerialName("resultData")
    val resultData: ResultData,
    @SerialName("status")
    val status: String,
    @SerialName("year")
    val year: Int
)