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
    val numbers: List<String>,
    val extraNumbers: List<String>?,
    val office: String,
    val millon: String?,
    val joker: String?,
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
            numbers = numbers,
            extraNumbers = extraNumbers,
            office = office,
            joker = joker,
            millon = millon,
            numLottery = numLottery,
            serie = serie,
            fraccion = fraccion,
            prize = prize,
            betPrice = betPrice,
            isWinner = isWinner
        )
    }
}
