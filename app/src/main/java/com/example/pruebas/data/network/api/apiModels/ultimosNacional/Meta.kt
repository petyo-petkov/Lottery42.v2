package com.example.pruebas.data.network.api.apiModels.ultimosNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("hasPrev")
    val hasPrev: Boolean,
    @SerialName("limit")
    val limit: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("totalPages")
    val totalPages: Int
)