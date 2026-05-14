package com.example.assignment2.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    /**
     * Formats ISO 8601 date string (e.g., "2025-03-08T15:00:00Z")
     * to human-readable format (e.g., "Mar 08, 2025").
     */
    fun formatIsoDate(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(isoDate)
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            if (date != null) outputFormat.format(date) else isoDate
        } catch (e: Exception) {
            isoDate
        }
    }
}
