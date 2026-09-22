package com.example.pruebas.presentation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HomeKey : NavKey

@Serializable
data class DetailKey(val ticketId: String) : NavKey

@Serializable
data class ExtraDetailKey(val ticketId: String) : NavKey

@Serializable
data object SettingsKey : NavKey
