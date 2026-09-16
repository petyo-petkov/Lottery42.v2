package com.example.pruebas.data.db

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.pruebas.data.toDisplayDate
import com.example.pruebas.domain.Ticket


@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey val id: String,
    val drawId: String,
    val gameType: String,
    val name: String,
    val cdc: String,
    val drawDate: String,
    val gameStatus: String,
    val bets: List<String>,
    val office: String,
    val reintegro: String?,
    val joker: String?,
    val stars: List<String>?,
    val millon: String?,
    val dreams: List<String>?,
    val numLottery: String?,
    val serie: String?,
    val fraccion: String?,

    val prize: String,
    val betPrice: String,
    val isWinner: Boolean
) {
    fun toDomain(): Ticket {
        return Ticket(
            id = id,
            drawId = drawId,
            gameType = gameType,
            name = name,
            cdc = cdc,
            drawDate = drawDate.toDisplayDate(),
            gameStatus = gameStatus,
            bets = bets,
            office = office,
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
            isWinner = isWinner
        )
    }
}
