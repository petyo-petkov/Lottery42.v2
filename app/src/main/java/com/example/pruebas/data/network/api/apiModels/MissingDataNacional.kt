package com.example.pruebas.data.network.api.apiModels

import kotlinx.serialization.Serializable

@Serializable
data class MissingDataNacional(
    val drawId: String,
    val fecha: String,
    val cierre: String,
    val precio: String,
    val status: String
)