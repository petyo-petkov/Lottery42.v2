package com.example.pruebas.presentation.detailScreen

sealed interface DetailIntent {
    data class LoadTicket(val ticketId: String) : DetailIntent
    data object CheckTicket : DetailIntent
    data object DeleteTicket : DetailIntent
    data object ToggleDeleteDialog : DetailIntent
    data object ToggleCheckDialog : DetailIntent
}
