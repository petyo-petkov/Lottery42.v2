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

}