package com.example.pruebas.data

import android.util.Log
import com.example.pruebas.domain.ScannerRepo
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class ScannerRepoImpl(private val scanner: GmsBarcodeScanner) : ScannerRepo {
    override fun startScann(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    launch {
                        send(barcode.rawValue)
                        Log.i("RawCODE", barcode.rawValue.toString())
                    }
                }.addOnFailureListener {
                    Log.e("ScannerRepo", it.message.toString())
                }
            awaitClose { }
        }
    }
}