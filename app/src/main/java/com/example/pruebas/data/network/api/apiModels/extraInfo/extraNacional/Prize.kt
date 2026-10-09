package com.example.pruebas.data.network.api.apiModels.extraInfo.extraNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Prize(
    @SerialName("category")
    val category: Int,
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("formattedPrize")
    val formattedPrize: String,
    @SerialName("prizeAmount")
    val prizeAmount: String,
    @SerialName("winners")
    val winners: Int
)