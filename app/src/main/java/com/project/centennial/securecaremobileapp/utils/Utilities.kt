package com.project.centennial.securecaremobileapp.utils

import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class Utilities {

    companion object {
        @JvmStatic
        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        fun isNumeric(toCheck: String): Boolean {
            val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
            return toCheck.matches(regex)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDateTime(originalDateString: String): String {
            var odt = OffsetDateTime.parse(originalDateString);
            var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
            return dtf.format(odt)

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDate(originalDateString: String): String {
            var odt = OffsetDateTime.parse(originalDateString);
            var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CANADA);
            return dtf.format(odt)

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getFormattedDate(): String {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CANADA)
            return currentDate.format(formatter)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getTimeDate(mins: Int): String {
            val currentTime = LocalTime.now()
            val hour = currentTime.hour
            val minute = currentTime.minute
            val newMinute = (minute + mins) % 60
            val newHour = (hour + (minute + mins) / 60) % 24
            return String.format("%02d:%02d:00", newHour, newMinute)
        }

    }
}

