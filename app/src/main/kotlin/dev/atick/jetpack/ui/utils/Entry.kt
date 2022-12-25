package dev.atick.jetpack.ui.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Entry<T>(
    val content: MutableState<T>,
    val error: MutableState<String?> = mutableStateOf(null)
)
