package dev.atick.jetpack.data

data class OxiMeterData(
    val index: Int,
    val timestamp: Long,
    val deviceId: Int,
    val battery: Double,
    val ppgSignal: Int,
    val heartRate: Int,
    val spO2: Double,
    val redAdc: Long,
    val irAdc: Long,
    val spO2Status: Int,
    val perfusionIndex: Double
) {
    override fun toString(): String {
        return "$index,$timestamp,$deviceId,$battery,$ppgSignal,$heartRate," +
            "$spO2,$redAdc,$irAdc,$spO2Status,$perfusionIndex"
    }
}
