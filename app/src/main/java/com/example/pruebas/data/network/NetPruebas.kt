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


        val numbers = "2,18,11,31,38,45"
        val drawId = "1323004113"

        val result = client.get{
            url {
                path("results", "primitiva", "check")
            }
            parameter("numbers", numbers)
            parameter("extraNumbers" ,"04,")
            parameter("drawId", drawId)
        }

        println(result.body<JsonObject>())
        return result.body()
    }


}