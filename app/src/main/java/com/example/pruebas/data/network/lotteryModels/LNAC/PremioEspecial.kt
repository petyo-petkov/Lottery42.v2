package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PremioEspecial(
    @SerialName("fila")
    val fila: Int? = null,
    @SerialName("fraccion")
    val fraccion: Int?  = null,
    @SerialName("literalPremio")
    val literalPremio: LiteralPremio?  = null,
    @SerialName("numero")
    val numero: Int? = null,
    @SerialName("orden")
    val orden: Int?  = null,
    @SerialName("premio")
    val premio: Int? = null,
    @SerialName("serie")
    val serie: Int? = null,
    @SerialName("showFolded")
    val showFolded: Boolean?
)