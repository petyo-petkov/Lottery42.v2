package com.example.pruebas.presentation.homeScreen

import com.example.pruebas.domain.Ticket

sealed interface HomeIntent {
    data class SelectTicket(val ticket: Ticket) : HomeIntent
    data class DeleteTicket(val ticket: Ticket) : HomeIntent
    object DeleteAll : HomeIntent
    data class ToggleDeleteDialog(val mode: DeleteDialogMode? = null) : HomeIntent
    object Scann : HomeIntent
    data class CheckTicket(val ticket: Ticket) : HomeIntent
}

enum class DeleteDialogMode {
    DELETE_ALL, DELETE_SINGLE
}
