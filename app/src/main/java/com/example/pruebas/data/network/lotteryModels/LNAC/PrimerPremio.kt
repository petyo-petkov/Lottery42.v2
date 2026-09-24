package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrimerPremio(
    @SerialName("alambre")
    val alambre: Int? = null,
    @SerialName("decimo")
    val decimo: String?,
    @SerialName("fila")
    val fila: Int?,
    @SerialName("literalPremio")
    val literalPremio: LiteralPremio?,
    @SerialName("orden")
    val orden: Int?,
    @SerialName("ordenFila")
    val ordenFila: Int?,
    @SerialName("prize")
    val prize: Int?,
    @SerialName("prizeType")
    val prizeType: String?,
    @SerialName("showFolded")
    val showFolded: Boolean?,
    @SerialName("tabla")
    val tabla: Int? = null
)