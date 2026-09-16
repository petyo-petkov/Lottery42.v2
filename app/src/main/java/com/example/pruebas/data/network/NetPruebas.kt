package com.example.pruebas.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject


class NetPruebas(){

//    val client =  HttpClient(Android) {
//        install(ContentNegotiation) {
//            json(Json {
//                ignoreUnknownKeys = true
//                coerceInputValues = true
//            })
//        }
//        defaultRequest {
//            // url("https://api.loteriasapi.com/api/v1/")
//            url("https://www.loteriasyapuestas.es/servicios/")
//
//            // header("Authorization", "Bearer ${BuildConfig.LOTTERY_API_KEY}")
//            header(HttpHeaders.UserAgent, "Mozilla/5.0 (Android; Mobile)")
//        }
//    }
//
//     suspend fun netPruebas(): JsonObject {
//
//        val urlString = "proximosv3?game_id=TODOS&num=5"
//        return client.get(urlString).body()
//    }


}