package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContenidosRelacionados(
    @SerialName("documentos")
    val documentos: List<Documento?>? = null,
    @SerialName("enlaces")
    val enlaces: List<String?>? = null,
    @SerialName("imagenes")
    val imagenes: List<Imagene?>? = null,
    @SerialName("noticias")
    val noticias: List<Noticia?>? = null,
    @SerialName("paginasLibres")
    val paginasLibres: List<String?>? = null,
    @SerialName("preguntasFrecuentes")
    val preguntasFrecuentes: List<String?>? = null,
    @SerialName("puntosDeVenta")
    val puntosDeVenta: List<String?>? = null,
)