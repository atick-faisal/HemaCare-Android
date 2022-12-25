package dev.atick.ble

import android.bluetooth.le.ScanResult
import kotlinx.coroutines.flow.Flow
import java.util.*

interface BleManager {

    fun scanBleDevices(timeout: Long): Flow<List<ScanResult>>
    suspend fun connect(macAddress: String): Boolean
    suspend fun readCharacteristic(uuid: UUID): ByteArray
    suspend fun writeCharacteristic(uuid: UUID, data: ByteArray)
    fun observeCharacteristic(uuid: UUID): Flow<ByteArray>
    suspend fun clearObserver(uuid: UUID): Boolean
    suspend fun disconnect(): Boolean
    suspend fun stopScan(): Boolean

}
