package dev.atick.jetpack.repository

import android.os.Bundle

interface SmartCareRepository {
    val isConnected: Boolean
    fun connect(onResult: (Boolean) -> Unit)
    fun disconnect(onSuccess: () -> Unit)
    fun startRecording(onReceiveData: (Bundle?) -> Unit)
}