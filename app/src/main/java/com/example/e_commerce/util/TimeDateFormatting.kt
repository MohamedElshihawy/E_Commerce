package com.example.e_commerce.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeDateFormatting {

    var formattedTimeAndDate: String = ""

    fun formatCurrentDateAndTime(currentTimeMillis: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        formattedTimeAndDate =
            "${dateFormat.format(Date(currentTimeMillis))}\\${
                timeFormat.format(
                    Date(
                        currentTimeMillis,
                    ),
                )
            }"

        return formattedTimeAndDate
    }
}
