package com.example.pruebas.data.network.lotteryModels.infoModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoLotteryPrize(
    @SerialName("category")
    val category: Int? = null,
    @SerialName("categoryName")
    val categoryName: String? = null,
    @SerialName("formattedPrize")
    val formattedPrize: String? = null,
    @SerialName("prizeAmount")
    val prizeAmount: String? = null,
    @SerialName("winners")
    val winners: Int? = null
)