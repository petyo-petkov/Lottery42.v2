package com.example.pruebas.data.network.lotteryModels.infoModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoLotteryGame(
    @SerialName("name")
    val name: String? = null,
    @SerialName("slug")
    val slug: String? = null
)