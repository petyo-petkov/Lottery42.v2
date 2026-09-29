package com.example.pruebas

import com.example.pruebas.data.network.NetPruebas
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun netPruebas() {
        runBlocking {
            NetPruebas().netPruebas()
        }
    }

    @Test
    fun urlPremioBONO_hasCorrectFormat() {
        val ticket = com.example.pruebas.domain.Ticket(
            drawId = "937601050",
            numbers = listOf("04,05,06,23,36,15", "01,02,03,22,25,16"),
            reintegro = "4"
        )
        val url = com.example.pruebas.data.network.urlsGetPremio.urlPremioBONO(ticket)
        val expected = "https://www.loteriasyapuestas.es/es/resultados/bonoloto/comprobar?drawId=937601050&modalidad=simple&bloque1=4y5y6y23y36y15&bloque2=1y2y3y22y25y16&reintegro=4"
        assertEquals(expected, url)
    }
}