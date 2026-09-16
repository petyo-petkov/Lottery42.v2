package com.example.pruebas.data.network.lotteryModels.scannModel

import kotlinx.serialization.Serializable

@Serializable
data class ScannData(
    val a: String,             // A
    val p: String,             // P
    val s: String,             // S
    val w: String,             // W
    val bets: List<String>,    // .1, .2, .3...
    val t: String,             // T
    val r: String              // R
)