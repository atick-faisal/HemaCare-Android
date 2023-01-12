package dev.atick.jetpack.repository

import android.os.Bundle
import android.os.Handler
import com.smartcare.oximetry.library.ConnectionManager
import dev.atick.jetpack.repository.SmartCareRepository
import java.util.concurrent.ThreadPoolExecutor
import javax.inject.Inject

class SmartCareRepositoryImpl @Inject constructor(
    private val executor: ThreadPoolExecutor,
    private val handler: Handler,
    private val connectionManager: ConnectionManager
): SmartCareRepository {

    override val isConnected: Boolean
        get() = connectionManager.isConnected

    override fun connect(onResult: (Boolean) -> Unit) {
        executor.execute {
            val success = connectionManager.connect()
            handler.post { onResult(success) }
        }
    }

    override fun disconnect(onSuccess: () -> Unit) {
        executor.execute {
            connectionManager.disconnect()
            while (connectionManager.isConnected) Thread.sleep(100)
            handler.post { onSuccess() }
        }
    }

    override fun startRecording(onReceiveData: (Bundle?) -> Unit) {
        executor.execute {
            connectionManager.startRecording()
            handler.post { connectionManager }
        }
    }
}