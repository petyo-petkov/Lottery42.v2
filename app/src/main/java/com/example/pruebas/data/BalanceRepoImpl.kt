package com.example.pruebas.data

import com.example.pruebas.domain.BalanceRepo
import com.example.pruebas.domain.Ticket
import com.example.pruebas.presentation.homeScreen.BalanceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BalanceRepoImpl: BalanceRepo {
    override fun getBalance(tickets: Flow<List<Ticket>>): Flow<BalanceState> {
       return tickets.map { ticket ->
           val ganado = ticket.sumOf { it.prize.toDoubleOrNull() ?: 0.0 }
           val gastado = ticket.sumOf { it.betPrice.toDoubleOrNull() ?: 0.0 }
           val balance = ganado - gastado
           val porcentaje = if (gastado > 0.0) ((balance) / gastado) * 100 else 0.0
           BalanceState(
               ganado = "%.2f €".format(ganado),
               gastado = "%.2f €".format(gastado),
               balance = "%.2f €".format(balance),
               porcentaje = "%.2f %%".format(porcentaje)
           )


       }
    }


}