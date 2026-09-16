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

    override fun getTicketByDrawId(drawId: String): Flow<Ticket?> {
        return dao.getByDrawId(drawId).map { it?.toDomain() }
    }
}
