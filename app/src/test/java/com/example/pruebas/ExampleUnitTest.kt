package com.example.pruebas

import com.example.pruebas.data.network.NetPruebas
import com.example.pruebas.data.network.webView.urlsGetPremio.urlPremioBONO
import com.example.pruebas.data.network.webView.urlsGetPremio.urlPremioEDMS
import com.example.pruebas.data.network.webView.urlsGetPremio.urlPremioELGR
import com.example.pruebas.data.network.webView.urlsGetPremio.urlPremioEMIL
import com.example.pruebas.data.parsePrize
import com.example.pruebas.data.toCurrencyFormat
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
            idSorteo = "937601050",
            numbers = listOf("04,05,06,23,36,15", "01,02,03,22,25,16"),
            reintegro = "4"
        )
        val url = urlPremioBONO(ticket)
        val expected = "https://www.loteriasyapuestas.es/es/resultados/bonoloto/comprobar?drawId=937601050&modalidad=simple&bloque1=4y5y6y23y36y15&bloque2=1y2y3y22y25y16&reintegro=4"
        assertEquals(expected, url)
    }

    @Test
    fun urlPremioEMIL_hasCorrectFormat() {
        val ticket = com.example.pruebas.domain.Ticket(
            idSorteo = "1254702089",
            numbers = listOf("08 20 24 01 29", "07 11 22 36 44"),
            stars = listOf("08 09", "07 08")
        )
        val url = urlPremioEMIL(ticket)
        val expected = "https://www.loteriasyapuestas.es/es/resultados/euromillones/comprobar?drawId=1254702089&modalidad=simple&bloque1=08y20y24y01y29&estrellas1=08y09&bloque2=07y11y22y36y44&estrellas2=07y08"
        assertEquals(expected, url)
    }

    @Test
    fun urlPremioEDMS_hasCorrectFormat() {
        val ticket = com.example.pruebas.domain.Ticket(
            idSorteo = "1176214043",
            numbers = listOf("28 27 1 2 3 16", "28 27 1 2 5 34"),
            dreams = listOf("4", "4")
        )
        val url = urlPremioEDMS(ticket)
        val expected = "https://www.loteriasyapuestas.es/es/resultados/eurodreams/comprobar?drawId=1176214043&modalidad=simple&bloque1=28y27y1y2y3y16&numero1=4&bloque2=28y27y1y2y5y34&numero2=4"
        assertEquals(expected, url)
    }

    @Test
    fun urlPremioELGR_hasCorrectFormat() {
        val ticket = com.example.pruebas.domain.Ticket(
            idSorteo = "940205013",
            numbers = listOf("28 27 1 2 3", "28 27 1 2 5", "28 27 1 2 8"),
            clave = listOf("4", "4", "3")
        )
        val url = urlPremioELGR(ticket)
        val expected = "https://www.loteriasyapuestas.es/es/resultados/gordo-primitiva/comprobar?drawId=940205013&modalidad=simple&bloque1=28y27y1y2y3&reintegro1=4&bloque2=28y27y1y2y5&reintegro2=4&bloque3=28y27y1y2y8&reintegro3=3"
        assertEquals(expected, url)
    }

    @Test
    fun ticketFromQrCode_fallbackIdWhenMissingA() {
        val rawData = "P=2;S=251;W=0"
        val ticket = com.example.pruebas.data.ticketFromQrCode(rawData)
        assertEquals(rawData, ticket.id)
    }

    @Test
    fun parsePrize_isCorrect() {
        assertEquals(2.5, "2,50".parsePrize(), 0.001)
        assertEquals(1234.56, "1.234,56".parsePrize(), 0.001)
        assertEquals(10.0, "10".parsePrize(), 0.001)
        assertEquals(0.0, "€".parsePrize(), 0.001)
        assertEquals(0.0, null.parsePrize(), 0.001)
    }

    @Test
    fun toCurrencyFormat_isCorrect() {
        val amount = "2.5"
        val formattedWithDecimals = amount.toCurrencyFormat(withDecimals = true)
        assertTrue(formattedWithDecimals.contains("2,50") || formattedWithDecimals.contains("2,5"))

        val formattedWithoutDecimals = amount.toCurrencyFormat(withDecimals = false)
        assertTrue(formattedWithoutDecimals.contains("2"))
    }
}