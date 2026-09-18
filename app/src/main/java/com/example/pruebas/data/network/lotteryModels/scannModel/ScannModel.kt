package com.example.pruebas.data.network.lotteryModels.scannModel

import kotlinx.serialization.Serializable

@Serializable
data class ScannData(
    val a: String? = null,             // A
    val p: String? = null,             // P
    val s: String? = null,             // S
    val w: String? = null,             // W
    val bets: List<String>? = null,    // .1, .2, .3...
    val t: String? = null,             // T
    val r: String? = null              // R
)