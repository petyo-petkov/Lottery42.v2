package com.example.pruebas.data.network.api.apiModels.check


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("additionalData")
    val additionalData: AdditionalData,
    @SerialName("checkedExtraNumbers")
    val checkedExtraNumbers: List<Int>,
    @SerialName("checkedNumbers")
    val checkedNumbers: List<Int>,
    @SerialName("drawDate")
    val drawDate: String,
    @SerialName("drawId")
    val drawId: String,
    @SerialName("extraNumbersMatched")
    val extraNumbersMatched: Int,
    @SerialName("game")
    val game: Game,
    @SerialName("hasResults")
    val hasResults: Boolean,
    @SerialName("isWinner")
    val isWinner: Boolean,
    @SerialName("mainNumbersMatched")
    val mainNumbersMatched: Int,
    @SerialName("matchedExtraNumbers")
    val matchedExtraNumbers: List<Int?> = emptyList(),
    @SerialName("matchedNumbers")
    val matchedNumbers: List<Int?> = emptyList(),
    @SerialName("winningCombination")
    val winningCombination: List<Int>,
    @SerialName("winningExtraNumbers")
    val winningExtraNumbers: List<Int>
)