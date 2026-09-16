package com.example.pruebas.di

import android.app.Application
import androidx.room3.Room
import com.example.pruebas.BuildConfig
import com.example.pruebas.data.ScannerRepoImpl
import com.example.pruebas.data.db.AppDatabase
import com.example.pruebas.data.db.LotteryDatabaseRepoImpl
import com.example.pruebas.data.network.NetworkRepoImpl
import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.ScannerRepo
import com.example.pruebas.presentation.ScannerViewModel
import com.example.pruebas.presentation.homeScreen.HomeScreenViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scannerModule: Module = module {

    single<GmsBarcodeScanner> {
        val app: Application = get()
        val options: GmsBarcodeScannerOptions =
            GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                    Barcode.FORMAT_CODE_128
                )
                .build()
        GmsBarcodeScanning.getClient(app.applicationContext, options)
    }

    singleOf(::ScannerRepoImpl) bind ScannerRepo::class

}

val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::ScannerViewModel)
}

val repositoryModule = module {
    singleOf(::LotteryDatabaseRepoImpl) bind LotteryDatabaseRepo::class
    singleOf(::NetworkRepoImpl) bind NetworkRepo::class
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "Lottery-DB"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single { get<AppDatabase>().lotteryDao() }

}

val networkModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            defaultRequest {
                url("https://api.loteriasapi.com/api/v1/")

                header("Authorization", "Bearer ${BuildConfig.LOTTERY_API_KEY}")
            }
        }
    }
}
