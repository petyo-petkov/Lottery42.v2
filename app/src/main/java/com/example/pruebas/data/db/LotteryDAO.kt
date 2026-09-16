package com.example.pruebas.data.db

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface LotteryDAO {
    @Query("SELECT * FROM tickets ORDER BY drawDate DESC")
    fun getAllTickets(): Flow<List<TicketEntity>>

    @Query("SELECT * FROM tickets WHERE drawId = :drawId")
    fun getByDrawId(drawId: String): Flow<TicketEntity?>

    @Insert
    suspend fun insert(lotteryEntity: TicketEntity)

    @Update
    suspend fun update(lotteryEntity: TicketEntity)


    @Delete
    suspend fun delete(lotteryEntity: TicketEntity)

    @Query("DELETE FROM tickets")
    suspend fun deleteAll()
}

