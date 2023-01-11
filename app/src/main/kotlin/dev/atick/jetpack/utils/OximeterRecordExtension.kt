package dev.atick.jetpack.utils

import dev.atick.jetpack.data.OxiMeterData

fun MutableList<OxiMeterData>.toCsv(): String {
    val sb = StringBuilder()
    sb.append(
        "index,timestamp,deviceId,battery,ppgSignal," +
            "heartRate,spO2,redAdc,irAdc,spO2Status,perfusionIndex\n"
    )
    forEach {
        sb.append(it.toString())
        sb.append("\n")
    }

    return sb.toString()
}