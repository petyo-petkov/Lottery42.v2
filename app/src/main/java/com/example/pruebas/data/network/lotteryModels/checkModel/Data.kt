package com.example.pruebas.data.network.lotteryModels.checkModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckLotteryData(
    @SerialName("game")
    val game: CheckLotteryGame? = null,
    @SerialName("drawDate")
    val drawDate: String? = null,
    @SerialName("drawId")
    val drawId: String? = null,
    @SerialName("isWinner")
    val isWinner: Boolean? = null,
    @SerialName("checkedNumbers")
    val checkedNumbers: List<Int>? = null,
    @SerialName("checkedExtraNumbers")
    val checkedExtraNumbers: List<Int?>? = null,
    @SerialName("extraNumbersMatched")
    val extraNumbersMatched: Int? = null,
    @SerialName("hasResults")
    val hasResults: Boolean? = null,
    @SerialName("mainNumbersMatched")
    val mainNumbersMatched: Int? = null,
    @SerialName("matchedExtraNumbers")
    val matchedExtraNumbers: List<Int?>? = null,
    @SerialName("matchedNumbers")
    val matchedNumbers: List<Int>? = null,
    @SerialName("prize")
    val prize: CheckLotteryPrize? = null,
    @SerialName("winningCombination")
    val winningCombination: List<Int>? = null,
    @SerialName("winningExtraNumbers")
    val winningExtraNumbers: List<Int?>? = null
)