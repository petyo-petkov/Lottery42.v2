package com.example.pruebas.data.network.api.apiModels.proximosNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String
)