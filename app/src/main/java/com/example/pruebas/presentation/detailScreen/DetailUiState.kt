package com.example.pruebas.presentation.detailScreen

import com.example.pruebas.presentation.homeScreen.TicketUiModel

data class DetailUiState(
    val ticketUiModel: TicketUiModel? = null,
    val isLoadingCheck: Boolean = false,
    val showCheckDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val error: String? = null
)
