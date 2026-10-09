package com.example.pruebas.data.network.api.apiModels.checkNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("additionalData")
    val additionalData: AdditionalData,
    @SerialName("checkedNumbers")
    val checkedNumbers: List<Int>,
    @SerialName("drawDate")
    val drawDate: String,
    @SerialName("drawId")
    val drawId: String,
    @SerialName("game")
    val game: Game,
    @SerialName("hasResults")
    val hasResults: Boolean,
    @SerialName("isWinner")
    val isWinner: Boolean,
    @SerialName("mainNumbersMatched")
    val mainNumbersMatched: Int,
    @SerialName("matchedNumbers")
    val matchedNumbers: List<Int?> = emptyList(),
    @SerialName("prize")
    val prize: Prize,
    @SerialName("winningCombination")
    val winningCombination: List<Int?> = emptyList()
)