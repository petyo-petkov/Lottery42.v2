package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Escrutinio(
    @SerialName("categoria")
    val categoria: Int?,
    @SerialName("categoriaLinea")
    val categoriaLinea: Int?,
    @SerialName("esPremioMayor")
    val esPremioMayor: Boolean?,
    @SerialName("ganadores")
    val ganadores: Int?,
    @SerialName("literalPremio")
    val literalPremio: LiteralPremio?,
    @SerialName("mostrarCategoria")
    val mostrarCategoria: Boolean?,
    @SerialName("ordenCategoria")
    val ordenCategoria: Int?,
    @SerialName("premio")
    val premio: String?,
    @SerialName("resultado")
    val resultado: Int?,
    @SerialName("tipo")
    val tipo: String?
)