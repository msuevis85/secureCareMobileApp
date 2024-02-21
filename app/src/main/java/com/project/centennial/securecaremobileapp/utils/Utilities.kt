package com.project.centennial.securecaremobileapp.utils

import android.text.TextUtils
import android.util.Patterns

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
    }
}

