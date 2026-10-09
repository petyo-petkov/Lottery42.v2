package com.example.pruebas.data.network.api.apiModels.checkNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionalData(
    @SerialName("amounts")
    val amounts: String,
    @SerialName("decimos")
    val decimos: List<Decimo>,
    @SerialName("listStatus")
    val listStatus: String,
    @SerialName("prizedNumbersInDraw")
    val prizedNumbersInDraw: Int
)