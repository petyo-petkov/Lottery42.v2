package com.example.pruebas.data.db

import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.Ticket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LotteryDatabaseRepoImpl(
    private val dao: LotteryDAO
) : LotteryDatabaseRepo {

    override fun getAllTickets(): Flow<List<Ticket>> {
        return dao.getAllTickets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTicketById(id: String): Flow<Ticket> {
        val ticket = dao.getById(id)
        return ticket.map { it?.toDomain() ?: Ticket() }
    }

    override suspend fun createTicket(ticket: Ticket) {
        dao.insert(ticket.toEntity())
    }

    override suspend fun updateTicket(ticket: Ticket) {
        dao.update(ticket.toEntity())
    }

    override suspend fun deleteTicket(ticket: Ticket) {
        dao.delete(ticket.toEntity())
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

}
