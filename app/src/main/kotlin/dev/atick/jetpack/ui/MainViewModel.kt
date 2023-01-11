package dev.atick.jetpack.ui

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.ViewModel
import com.smartcare.oximetry.library.ConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.atick.jetpack.data.OxiMeterData
import dev.atick.jetpack.ui.id.IdUiState
import dev.atick.jetpack.ui.images.ImageSelectionUiState
import dev.atick.jetpack.ui.smartcare.SmartCareState
import dev.atick.jetpack.ui.smartcare.SmartCareUiState
import dev.atick.jetpack.utils.toCsv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val executor: ThreadPoolExecutor
) : ViewModel() {

    companion object {
        private const val RECORDING_DURATION = 3 // MINUTES
        private const val SAMPLING_RATE = 100
        private const val N_DATA_POINTS = RECORDING_DURATION * 60 * SAMPLING_RATE
    }

    private val _idUiState = MutableStateFlow(IdUiState())
    val idUiState: StateFlow<IdUiState>
        get() = _idUiState

    private val _imageSelectionUiState = MutableStateFlow(ImageSelectionUiState())
    val imageSelectionUiState: StateFlow<ImageSelectionUiState>
        get() = _imageSelectionUiState

    private val _smartCareUiState = MutableStateFlow(SmartCareUiState())
    val smartCareUiState: StateFlow<SmartCareUiState>
        get() = _smartCareUiState

    private val recording = mutableListOf<OxiMeterData>()

    private val handler = HandlerCompat.createAsync(Looper.getMainLooper()) {
        val index = it.data.getInt(ConnectionManager.KEY_DATA_COUNTER)
        val timestamp = it.data.getLong(ConnectionManager.KEY_DATA_PACKAGE_RECEIVED)
        val deviceId = it.data.getInt(ConnectionManager.KEY_DATA_DEVICE_ID)
        val battery = it.data.getDouble(ConnectionManager.KEY_DATA_BATTERY)
        val ppgSignal = it.data.getInt(ConnectionManager.KEY_DATA_PLETH)
        val heartRate = it.data.getInt(ConnectionManager.KEY_DATA_PULSE)
        val spO2 = it.data.getDouble(ConnectionManager.KEY_DATA_SPO2)
        val redAdc = it.data.getLong(ConnectionManager.KEY_DATA_REDADC)
        val irAdc = it.data.getLong(ConnectionManager.KEY_DATA_IRADC)
        val spO2Status = it.data.getInt(ConnectionManager.KEY_DATA_SPO2STATUS)
        val perfusionIndex = it.data.getDouble(ConnectionManager.KEY_DATA_PERFUSIONINDEX)

        recording.add(
            OxiMeterData(
                index, timestamp, deviceId, battery, ppgSignal,
                heartRate, spO2, redAdc, irAdc, spO2Status, perfusionIndex
            )
        )

        if (recording.size > N_DATA_POINTS) {
            saveRecording()
            recording.clear()
            _smartCareUiState.update { state ->
                state.copy(
                    smartCareState = SmartCareState.RecordingComplete
                )
            }
        }

        _smartCareUiState.update { state ->
            state.copy(
                heartRate = heartRate
            )
        }

        true
    }

     private val connectionManager = ConnectionManager(context, handler)


    fun setPatientId(patientId: String) {
        _idUiState.update { state ->
            state.copy(patientId = patientId)
        }
    }

    fun setSelectedImages(imageUris: List<Uri>) {
        _imageSelectionUiState.update { state ->
            state.copy(
                imageUris = imageUris
            )
        }
    }

    fun toggleConnection() {
        if (smartCareUiState.value.smartCareState == SmartCareState.Disconnected) {
            connect()
        } else {
            disconnect()
        }
    }

    private fun connect() {
        executor.execute {
            Timber.i("CONNECTING ... ")
            _smartCareUiState.update { state ->
                state.copy(smartCareState = SmartCareState.Connecting)
            }

            val connected = connectionManager.connect()
            if (connected) {
                Timber.d("CONNECTED")
                _smartCareUiState.update { state ->
                    state.copy(smartCareState = SmartCareState.Connected)
                }
                connectionManager.startRecording()
                _smartCareUiState.update { state ->
                    state.copy(smartCareState = SmartCareState.Recording)
                }
            } else {
                Timber.d("DISCONNECTED")
                _smartCareUiState.update { state ->
                    state.copy(smartCareState = SmartCareState.Disconnected)
                }
            }
        }
    }

    private fun disconnect() {
        executor.execute {
            Timber.i("DISCONNECTING ... ")
            _smartCareUiState.update { state ->
                state.copy(smartCareState = SmartCareState.Disconnecting)
            }
            connectionManager.disconnect()
            while (connectionManager.isConnected) Thread.sleep(100)
            Timber.d("DISCONNECTED")
            _smartCareUiState.update { state ->
                state.copy(smartCareState = SmartCareState.Disconnected)
            }
        }
    }

    private fun saveRecording() {
        val timestamp = SimpleDateFormat("dd_M_yyyy_hh_mm_ss", Locale.US).format(Date())
        val csvData = recording.toCsv()
        val savePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        Files.createDirectories(savePath.toPath())
        val myExternalFile = File(
            savePath,
            "${timestamp}.csv"
        )

        try {
            val fos = FileOutputStream(myExternalFile)
            fos.write(csvData.toByteArray())
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}