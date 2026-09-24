package com.example.pruebas.presentation.homeScreen

import com.example.pruebas.domain.Ticket

enum class PrizeStatus {
    NO_PRIZE,   // No premiado (0.0 €)
    WINNER,     // Premiado (> 0.0 €)
    UNKNOWN     // No comprobado aún
}

data class BalanceState(
    val ganado: String = "",
    val gastado: String = "",
    val balance: String = "",
    val porcentaje: String = ""
)

data class TicketUiModel(
    val ticket: Ticket,
    val height: Int,
    val lotteryColorHex: Long,
    val prizeStatus: PrizeStatus,
    val formattedPrize: String,
    val isChecked: Boolean = false
)

data class HomeUiState(
    val tickets: List<TicketUiModel> = emptyList(),
    val balance: BalanceState = BalanceState(),
    val showDeleteDialog: Boolean = false,
    val isScanning: Boolean = false
)
