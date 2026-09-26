package com.example.pruebas.presentation.extraDetailScreen

sealed interface ExtraDetailIntent {
    data class LoadInfo(val ticketId: String) : ExtraDetailIntent
}
