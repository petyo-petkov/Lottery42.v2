package com.example.pruebas.presentation

sealed interface ScannerIntent {
    data object StartScan : ScannerIntent
}
