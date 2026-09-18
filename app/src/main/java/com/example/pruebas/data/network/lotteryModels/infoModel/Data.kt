package com.example.pruebas.data.network.lotteryModels.infoModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoLotteryData(
    @SerialName("combination")
    val combination: List<Int?>? = null,
    @SerialName("dayOfWeek")
    val dayOfWeek: String? = null,
    @SerialName("drawDate")
    val drawDate: String? = null,
    @SerialName("drawId")
    val drawId: String? = null,
    @SerialName("game")
    val game: InfoLotteryGame? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("jackpot")
    val jackpot: String? = null,
    @SerialName("jackpotFormatted")
    val jackpotFormatted: String? = null,
    @SerialName("prizes")
    val prizes: List<InfoLotteryPrize?>? = null,
    @SerialName("resultData")
    val resultData: ResultData? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("year")
    val year: Int? = null
)