package com.example.pruebas.data

import com.example.pruebas.data.network.lotteryModels.scannModel.ScannData

//fun parseRawData(raw: String): ScannData {
//    val map = mutableMapOf<String, String>()
//    val bets = mutableListOf<String>()
//
//    // Separamos por punto y coma para obtener los bloques principales
//    raw.split(";").forEach { segment ->
//        if (segment.startsWith(".")) {
//            // Este es el bloque de las apuestas (.1=... .2=...)
//            // Lo separamos por los puntos que marcan cada nueva apuesta
//            segment.split(".").filter { it.isNotEmpty() }.forEach { bet ->
//                val value = bet.split("=").getOrNull(1)
//                if (value != null) bets.add(value)
//            }
//        } else {
//            // Bloques normales como A=..., P=...
//            val keyValue = segment.split("=")
//            if (keyValue.size == 2) {
//                map[keyValue[0]] = keyValue[1]
//            }
//        }
//    }
//
//    return ScannData(
//        a = map["A"] ?: "",
//        p = map["P"] ?: "",
//        s = map["S"] ?: "",
//        w = map["W"] ?: "",
//        bets = bets,
//        t = map["T"] ?: "",
//        r = map["R"] ?: ""
//    )
//}