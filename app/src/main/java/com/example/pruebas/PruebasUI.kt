package com.example.pruebas

import kotlin.collections.plusAssign

// Primitiva:    A=1322101021603764847669065183556213;P=1;S=10910SEP26:1;W=0;.1=041921323647;T=50065-1;R=9;J=NO;
// Euromillones: A=1322207020606684281645191100520841;P=7;S=07311SEP26:1;W=0;.1=0408303643:0308;T=51995-0;RI=11[S=07311SEP26:1,FXX92887];
// Bonoloto:     A=1321902020120280171942842258210547;P=2;S=25108SEP26:1;W=0;.1=040812273049.2=041519274249.3=081215193042;T=50050-1;R=2;




fun parse(rawData: String): Map<String, String> {
    val data = rawData.split(";").filter { it.contains("=") }

    // Extraemos las apuestas una sola vez fuera del bucle
    val betsToken = data.find { it.startsWith(".") }
    val betsString = betsToken?.substringAfter(".")?.split(".")?.joinToString(",") ?: ""

    return data.associate { pair ->
        if (pair.startsWith(".")) {
            "bets" to betsString
        } else {
            val (key, value) = pair.split("=", limit = 2)
            key to value
        }
    }
}

// Función auxiliar para dar formato "XX XX XX"
fun formatNumbers(numberString: String?): String {
    if (numberString.isNullOrEmpty()) return ""
    return numberString.chunked(2).joinToString(" ")
}

fun prueba(){

    val rawData =  "A=1322101021603764847669065183556213;P=1;S=10910SEP26:1;W=0;.1=040812273049.2=041519274249.3=081215193042;T=50065-1;R=9;J=NO;"

    val pairs = parse(rawData)

    val id = pairs["A"]
    val cdc = pairs["S"]?.take(3)
    val drawId = pairs["A"]?.take(5) + "02" + cdc
    val gameType = pairs["P"]
    val date = pairs["S"]?.substringBefore(":")?.takeLast(7)

    val bets: MutableList<String> = mutableListOf()
    val rawBets = pairs["bets"]?.removeSurrounding("[", "]")
            ?.split(",")
            ?: emptyList()



    bets += rawBets.map { it.substringAfter("=").chunked(2).joinToString(" ")}


    println(rawBets)
    println(bets)

}