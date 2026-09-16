package com.example.pruebas.data

import com.example.pruebas.domain.Ticket
import kotlin.random.Random

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

fun createTicket(rawData: String): Ticket {
    // Primitiva:        A=1322101021603764847669065183556213;P=1;S=10910SEP26:1;W=0;.1=041921323647;T=50065-1;R=9;J=NO;
    // Euromillones:     A=1322207020606684281645191100520841;P=7;S=07311SEP26:1;W=0;.1=0408303643:0308;T=51995-0;RI=11[S=07311SEP26:1,FXX92887];
    // Bonoloto:         A=1321902020120280171942842258210547;P=2;S=25108SEP26:1;W=0;.1=040812273049.2=041519274249.3=081215193042;T=50050-1;R=2;
    // EuroDreams:       A=1322514020920309269333983963949645;P=14;S=07414SEP26:1;W=0;.1=021113263338:01;T=50065-1;
    // Loteria Nacional: A=1322610020811439573582346847356130;P=10;S=07517SEP26:1;W=0;N=81030;F=1;SE=5;DF=1;T=50120-0;
    val pairs = parse(rawData)

    val id = pairs["A"] ?: ""
    val cdc = pairs["S"]?.take(3) ?: ""
    var drawId = ""
    val date = pairs["S"]?.substringBefore(":")?.takeLast(7)?.toStoreDate() ?: ""
    val (gameType, name) = when (pairs["P"]) {
        "1" -> "primitiva" to "La Primitiva"
        "2" -> "bonoloto" to "Bonoloto"
        "4" -> "gordo" to "El Gordo"
        "7" -> "euromillones" to "Euromillones"
        "10" -> "nacional" to "Loteria Nacional"
        "14" -> "nacional" to "Eurodreams"
        else -> "Unknown" to "Desconosido"
    }
    val rawBets = pairs["bets"]?.removeSurrounding("[", "]")
        ?.split(",")
        ?: emptyList()

    val bets: MutableList<String> = mutableListOf()
    val stars: MutableList<String> = mutableListOf()
    val dreams: MutableList<String> = mutableListOf()
    var millon: String? = ""
    var reintegro: String? = ""
    var joker: String? = ""
    var numLottery: String? = ""
    var serie: String? = ""
    var fraccion: String? = ""
    var betPrice = ""

    val prize = Random.nextDouble(0.0,800.0).toString()

    when (gameType) {
        "primitiva" -> {
            drawId = pairs["A"]?.take(5) + "04" + cdc
            bets += rawBets.map { it.substringAfter("=").chunked(2).joinToString(",") }
            reintegro = pairs["R"]
            joker = pairs["J"]
            val price = (bets.size * 1.0 )
            if (joker != "NO") price + 1.0
            betPrice = price.toString()
        }

        "bonoloto" -> {
            drawId = pairs["A"]?.take(5) + "01" + cdc
            bets += rawBets.map { it.substringAfter("=").chunked(2).joinToString(",") }
            reintegro = pairs["R"]
            betPrice = (bets.size * 0.5 ).toString()

        }

        "eurodreams" -> {
            drawId = pairs["A"]?.take(5) + "14" + cdc
            bets += rawBets.map {
                it.substringAfter("=").substringBefore(":").chunked(2).joinToString(",")
            }
            dreams += rawBets.map {
                it.substringAfter(":")
            }
            betPrice = (bets.size * 2.5 ).toString()
        }

        "euromillones" -> {
            drawId = pairs["A"]?.take(5) + "02" + cdc
            bets += rawBets.map {
                it.substringAfter("=").substringBefore(":").chunked(2).joinToString(",")
            }
            stars += rawBets.map {
                it.substringAfter(":").chunked(2).joinToString(",")
            }
            val regexMillon = """([A-Z0-9]+)]""".toRegex()
            millon = pairs["RI"]?.let { regexMillon.find(it)?.groupValues?.get(1) }
            betPrice = (bets.size * 2.5 ).toString()
        }

        "godo" -> {
            TODO()
        }

        "nacional" -> {
            drawId = pairs["A"]?.take(5) + "09" + cdc
            numLottery = pairs["N"]
            serie = pairs["SE"]
            fraccion = pairs["F"]
            betPrice = "3"
        }

    }




    return Ticket(
        id = id,
        drawId = drawId,
        gameType = gameType,
        name = name,
        cdc = cdc,
        drawDate = date,
        gameStatus = pairs["W"] ?: "",
        bets = bets,
        office = pairs["T"] ?: "",
        reintegro = reintegro,
        joker = joker,

        stars = stars,
        millon = millon,
        dreams = dreams,
        numLottery = numLottery,
        serie = serie,
        fraccion = fraccion,
        prize = prize,
        betPrice = betPrice,
        isWinner = false
    )


}
