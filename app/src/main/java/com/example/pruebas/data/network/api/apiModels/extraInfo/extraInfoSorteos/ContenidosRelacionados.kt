package com.example.pruebas.data.network.api.apiModels.extraInfo.extraInfoSorteos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContenidosRelacionados(
    @SerialName("documentos")
    val documentos: List<String?> = emptyList(),
    @SerialName("enlaces")
    val enlaces: List<String?> = emptyList(),
    @SerialName("imagenes")
    val imagenes: List<String?> = emptyList(),
    @SerialName("noticias")
    val noticias: List<Noticia>,
    @SerialName("paginasLibres")
    val paginasLibres: List<String?> = emptyList(),
    @SerialName("preguntasFrecuentes")
    val preguntasFrecuentes: List<String?> = emptyList(),
    @SerialName("puntosDeVenta")
    val puntosDeVenta: List<String?> = emptyList()
)