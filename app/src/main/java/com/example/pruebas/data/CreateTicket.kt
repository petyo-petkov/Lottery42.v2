package com.example.pruebas.data

import com.example.pruebas.domain.LotteryGame
import com.example.pruebas.domain.Ticket

fun parse(rawData: String): Map<String, String> {
    val data = rawData.split(";").filter { it.contains("=") }

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
    val pairs = parse(rawData)

    val id = pairs["A"] ?: ""
    val cdc = pairs["S"]?.take(3) ?: ""
    val gameStatus = pairs["W"] ?: ""
    val office = pairs["T"] ?: ""
    val date = pairs["S"]?.substringBefore(":")?.takeLast(7)?.toStoreDate() ?: ""
    
    val game = LotteryGame.fromPValue(pairs["P"])
    
    val rawBets = pairs["bets"]?.removeSurrounding("[", "]")
        ?.split(",")
        ?: emptyList()

    val numbers: MutableList<String> = mutableListOf()
    val stars = mutableListOf<String>()
    val dreams = mutableListOf<String>()
    var reintegro: String? = ""
    var millon: String? = ""
    var joker: String? = ""
    var claves = mutableListOf<String>()
    var numLottery: String? = ""
    var serie: String? = ""
    var fraccion: String? = ""

    when (game) {
        is LotteryGame.Primitiva -> {
            numbers += rawBets.map { it.substringAfter("=").chunked(2).joinToString(",") }
            reintegro = pairs["R"]
            joker = pairs["J"]
        }

        is LotteryGame.Bonoloto -> {
            numbers += rawBets.map { it.substringAfter("=").chunked(2).joinToString(",") }
            reintegro = pairs["R"]
        }

        is LotteryGame.Eurodreams -> {
            numbers += rawBets.map {
                it.substringAfter("=").substringBefore(":").chunked(2).joinToString(",")
            }
            dreams += rawBets.map { it.substringAfter(":") }
        }

        is LotteryGame.Euromillones -> {
            numbers += rawBets.map {
                it.substringAfter("=").substringBefore(":").chunked(2).joinToString(",")
            }
            stars += rawBets.map {
                it.substringAfter(":").chunked(2).joinToString(",")
            }
            val regexMillon = """([A-Z0-9]+)]""".toRegex()
            millon = pairs["RI"]?.let { regexMillon.find(it)?.groupValues?.get(1) }
        }

        is LotteryGame.Gordo -> {
            numbers += rawBets.map {
                it.substringAfter("=").substringBefore(":").chunked(2).joinToString(",")
            }
            claves += rawBets.map { it.substringAfter(":") }
        }

        is LotteryGame.Nacional -> {
            numLottery = pairs["N"]
            serie = pairs["SE"]
            fraccion = pairs["F"]
        }
        else -> {}
    }

    val betPrice = game.calculatePrice(numbers.size.coerceAtLeast(1), joker != "NO" && joker != null)

    return Ticket(
        id = id,
        drawId = "${id.take(5)}${game.apiId}$cdc",
        gameType = game.type,
        name = game.name,
        cdc = cdc,
        drawDate = date,
        gameStatus = gameStatus,
        office = office,
        numbers = numbers,
        prize = "0.0",
        betPrice = betPrice.toString(),
        isWinner = false,
        joker = joker,
        reintegro = reintegro,
        stars = stars,
        millon = millon,
        dreams = dreams,
        numLottery = numLottery,
        serie = serie,
        fraccion = fraccion,
        clave = claves
    )
}
