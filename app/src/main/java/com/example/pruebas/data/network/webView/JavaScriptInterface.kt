package com.example.pruebas.data.network.webView

import android.webkit.JavascriptInterface

class JavaScriptInterface {
    class JavaScriptInterface(private val onDataReceived: (String) -> Unit) {
        @JavascriptInterface
        fun sendData(data: String) {
            onDataReceived(data)
        }
    }
}