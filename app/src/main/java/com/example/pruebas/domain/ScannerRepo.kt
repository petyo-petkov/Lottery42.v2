package com.example.pruebas.domain

import kotlinx.coroutines.flow.Flow

interface ScannerRepo{
    fun startScann() : Flow<String?>
}
