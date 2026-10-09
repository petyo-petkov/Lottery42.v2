package com.example.pruebas.data.network.api.apiModels.extraInfo.extraEurodreams


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String
)