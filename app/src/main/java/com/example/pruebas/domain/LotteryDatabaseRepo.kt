package com.example.pruebas.domain

import kotlinx.coroutines.flow.Flow

interface LotteryDatabaseRepo {

    fun getAllTickets(): Flow<List<Ticket>>

    suspend fun createTicket(ticket: Ticket)

    suspend fun updateTicket(ticket: Ticket)

    suspend fun deleteTicket(ticket: Ticket)

    suspend fun deleteAll()

    fun getTicketByDrawId(drawId: String): Flow<Ticket?>
}



