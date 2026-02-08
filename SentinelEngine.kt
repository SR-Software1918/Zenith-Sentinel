package com.zenith.sentinel

import java.util.Calendar

class SentinelEngine {

    fun determineMode(timeData: WorldTimeResponse): String {
        val hour = try {
            // Parses "2024-05-21T15:30:..."
            timeData.datetime.substring(11, 13).toInt()
        } catch (e: Exception) {
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        }

        return when {
            hour in 5..11 -> "MORNING_VIGIL"
            hour in 12..16 -> "PEAK_COGNITION"
            hour in 17..21 -> "EVENING_CONVERGENCE"
            else -> "RELAXATION_PROTOCOL"
        }
    }

    fun getQueryForMode(mode: String): String {
        return when (mode) {
            "MORNING_VIGIL" -> "productivity"
            "PEAK_COGNITION" -> "advanced_science"
            "EVENING_CONVERGENCE" -> "history"
            else -> "humor"
        }
    }
}