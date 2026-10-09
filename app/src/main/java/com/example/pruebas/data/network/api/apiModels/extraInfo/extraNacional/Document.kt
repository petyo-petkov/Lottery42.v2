package com.example.pruebas.data.network.api.apiModels.extraInfo.extraNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Document(
    @SerialName("error")
    val error: String? = null,
    @SerialName("originalUrl")
    val originalUrl: String,
    @SerialName("r2Url")
    val r2Url: String,
    @SerialName("status")
    val status: String,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String
)