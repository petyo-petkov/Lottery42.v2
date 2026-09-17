package com.example.pruebas.data.network.lotteryModels.checkModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("game")
    val game: Game,
    @SerialName("drawDate")
    val drawDate: String,
    @SerialName("drawId")
    val drawId: String,
    @SerialName("isWinner")
    val isWinner: Boolean,
    @SerialName("checkedNumbers")
    val checkedNumbers: List<Int>,
    @SerialName("checkedExtraNumbers")
    val checkedExtraNumbers: List<Int?>,
    @SerialName("extraNumbersMatched")
    val extraNumbersMatched: Int,
    @SerialName("hasResults")
    val hasResults: Boolean,
    @SerialName("mainNumbersMatched")
    val mainNumbersMatched: Int,
    @SerialName("matchedExtraNumbers")
    val matchedExtraNumbers: List<Int?>? = null,
    @SerialName("matchedNumbers")
    val matchedNumbers: List<Int>,
    @SerialName("prize")
    val prize: Prize? = null,
    @SerialName("winningCombination")
    val winningCombination: List<Int>,
    @SerialName("winningExtraNumbers")
    val winningExtraNumbers: List<Int?>? = null
)