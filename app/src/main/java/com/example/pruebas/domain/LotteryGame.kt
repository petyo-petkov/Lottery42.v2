package com.example.pruebas.domain

sealed class LotteryGame(
    val pValue: String,
    val name: String,
    val type: String,
    val apiId: String,
    val colorHex: Long
) {
    abstract fun calculatePrice(betCount: Int, hasJoker: Boolean = false): Double

    object Primitiva : LotteryGame("1", "La Primitiva", "primitiva", "04", 0xFF43A047) {
        override fun calculatePrice(betCount: Int, hasJoker: Boolean) =
            (betCount * 1.0) + (if (hasJoker) 1.0 else 0.0)
    }

    object Bonoloto : LotteryGame("2", "Bonoloto", "bonoloto", "01", 0xFF98A065) {
        override fun calculatePrice(betCount: Int, hasJoker: Boolean) = betCount * 0.5
    }

    object Gordo : LotteryGame("4", "El Gordo", "gordo", "05", 0xFFC0392B) {
        override fun calculatePrice(betCount: Int, hasJoker: Boolean) = betCount * 1.5
    }

    object Euromillones : LotteryGame("7", "Euromillones", "euromillones", "02", 0xFF283593) {
        override fun calculatePrice(betCount: Int, hasJoker: Boolean) = betCount * 2.5
    }

    object Nacional : LotteryGame("10", "Loteria Nacional", "nacional", "09", 0xFF0277BD) {
        override fun calculatePrice(betCount: Int, hasJoker: Boolean) = 3.0
    }

    object Eurodreams : LotteryGame("14", "Eurodreams", "eurodreams", "14", 0xFF8E24AA) {
        override fun calculatePrice(betCount: Int, hasJoker: Boolean) = betCount * 2.5
    }

    object Unknown : LotteryGame("", "Desconocido", "unknown", "", 0xFF000000) {
        override fun calculatePrice(betCount: Int, hasJoker: Boolean) = 0.0
    }

    companion object {
        fun fromPValue(pValue: String?): LotteryGame = when (pValue) {
            "1" -> Primitiva
            "2" -> Bonoloto
            "4" -> Gordo
            "7" -> Euromillones
            "10" -> Nacional
            "14" -> Eurodreams
            else -> Unknown
        }

        fun fromType(type: String?): LotteryGame = when (type) {
            "primitiva" -> Primitiva
            "bonoloto" -> Bonoloto
            "gordo" -> Gordo
            "euromillones" -> Euromillones
            "nacional" -> Nacional
            "eurodreams" -> Eurodreams
            else -> Unknown
        }
    }
}
