package com.example.pruebas.data.network

import android.util.Log
import com.example.pruebas.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject


class NetPruebas() {

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        defaultRequest {
            url("https://api.loteriasapi.com/api/v1/")


            header("Authorization", "Bearer ${BuildConfig.LOTTERY_API_KEY}")
            header(HttpHeaders.UserAgent, "Mozilla/5.0 (Android; Mobile)")
        }
    }

    suspend fun netPruebas() : JsonObject {

        val numbers = "10,44,46,31,38,41"
        val numbers2 = "45,7,40,31,38,2"
        val reintegro = "04"
        val drawId = "1323004113"
        val gameType = "euromillones"
        val date = "2026-09-18"

        val result = client.get{
            url {
                path("results", gameType, "date", date)           // https://api.loteriasapi.com/api/v1/results/bonoloto/date/2026-09-21
            }
            //parameter("gameType", gameType)
            //parameter("date", date)
        }

        println("URL: ${result.request.url}")
        println(result.body<JsonObject>())
        return result.body()
    }
}