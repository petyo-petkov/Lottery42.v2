package com.example.pruebas.domain

import com.example.pruebas.presentation.homeScreen.BalanceState
import kotlinx.coroutines.flow.Flow

interface BalanceRepo {

    fun getBalance(tickets: Flow<List<Ticket>>) : Flow<BalanceState>

}