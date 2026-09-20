package com.example.pruebas.presentation.homeScreen

import com.example.pruebas.domain.Ticket

object TicketUiMapper {
    fun toUiModel(ticket: Ticket): TicketUiModel {
        val prizeValue = ticket.prize.toDoubleOrNull() ?: 0.0
        val status = when {
            !ticket.isChecked -> PrizeStatus.UNKNOWN
            prizeValue == 0.0 -> PrizeStatus.NO_PRIZE
            else -> PrizeStatus.WINNER
        }

        return TicketUiModel(
            ticket = ticket,
            height = calculateHeight(prizeValue),
            lotteryColorHex = ticket.lotteryGame.colorHex,
            formattedPrize = ticket.prize,
            isChecked = ticket.isChecked,
            prizeStatus = status
        )
    }

    private fun calculateHeight(prize: Double): Int {
        return when {
            prize > 800000.0 -> 200
            prize > 500000.0 -> 180
            prize > 30000.0 -> 160
            prize > 15000.0 -> 140
            prize > 1.0 -> 120
            else -> 100
        }
    }
}
