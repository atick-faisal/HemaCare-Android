package dev.atick.core.extensions

import android.util.Patterns
import java.util.regex.Pattern

fun CharSequence?.isEmailValid(): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return !isNullOrEmpty() && pattern.matcher(this).matches()
}

fun CharSequence?.isPasswordValid(): Boolean {
    // ... Letters or Numbers. Min 8 Max 20 words
    val passwordRegex = "^(?=.*[0-9])(?=.*[a-z]).{8,20}$"
    val pattern: Pattern = Pattern.compile(passwordRegex)
    return !isNullOrEmpty() && pattern.matcher(this).matches()
}