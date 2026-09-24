package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LiteralPremio(
    @SerialName("ca")
    val ca: String?,
    @SerialName("en")
    val en: String?,
    @SerialName("es")
    val es: String?,
    @SerialName("eu")
    val eu: String?,
    @SerialName("gl")
    val gl: String?,
    @SerialName("va")
    val va: String?
)