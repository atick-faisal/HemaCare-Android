package dev.atick.jetpack.ui.smartcare

data class SmartCareUiState(
    val heartRate: Int = 0,
    val smartCareState: SmartCareState = SmartCareState.Disconnected
)

sealed class SmartCareState(val description: String) {
    object Connecting : SmartCareState("CONNECTING ... ")
    object Connected : SmartCareState("CONNECTED")
    object Recording : SmartCareState("RECORDING ... ")
    object RecordingComplete: SmartCareState("RECORDING COMPLETE")
    object Disconnecting : SmartCareState("DISCONNECTING ... ")
    object Disconnected : SmartCareState("DISCONNECTED")
}
