package com.example.pruebas.presentation.homeScreen

sealed interface HomeIntent {
    data object DeleteAll : HomeIntent
    data object ToggleDeleteDialog : HomeIntent
    data object Scann : HomeIntent
}
