package dev.atick.jetpack.utils

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import dev.atick.core.extensions.hasPermission
import dev.atick.core.extensions.permissionLauncher
import dev.atick.core.extensions.resultLauncher
import timber.log.Timber
import javax.inject.Inject

class PermissionUtils @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter?
) {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var bleLauncher: ActivityResultLauncher<Intent>

    fun initialize(
        activity: ComponentActivity,
        onSuccess: () -> Unit
    ) {
        permissionLauncher = activity.permissionLauncher {
            Timber.i("ALL PERMISSIONS GRANTED")
            enableBluetooth()
        }

        bleLauncher = activity.resultLauncher {
            Timber.i("BLUETOOTH IS ENABLED")
            onSuccess.invoke()
        }
    }

    private fun isAllPermissionsProvided(activity: ComponentActivity): Boolean {
        return isBluetoothPermissionGranted(activity) &&
            isLocationPermissionGranted(activity) &&
            // isStoragePermissionGranted(activity) &&
            bluetoothAdapter?.isEnabled ?: false
    }

    fun setupBluetooth(activity: ComponentActivity) {
        if (bluetoothAdapter == null) activity.finishAffinity()
        // showPermissionRationale(activity)
        if (!isAllPermissionsProvided(activity)) {
            askForPermissions()
        }
    }

    private fun enableBluetooth() {
        val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        bleLauncher.launch(enableIntent)
    }

    private fun askForPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            // Manifest.permission.READ_EXTERNAL_STORAGE,
            // Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.addAll(
                listOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        }
        permissionLauncher.launch(permissions.toTypedArray())
    }

//    private fun showPermissionRationale(activity: ComponentActivity) {
//        if (!isAllPermissionsProvided(activity)) {
//            activity.showAlertDialog(
//                title = "Permission Required",
//                message = "This app requires Bluetooth connection " +
//                    "to work properly. Please provide Bluetooth permission. " +
//                    "Scanning for BLE devices also requires Location Access " +
//                    "Permission. However, location information will NOT be" +
//                    "used for tracking.",
//                onApprove = {
//                    Timber.i("Permission Rationale Approved")
//                    askForPermissions()
//                },
//                onCancel = { activity.finishAffinity() }
//            )
//        }
//    }

    private fun isBluetoothPermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.hasPermission(Manifest.permission.BLUETOOTH_SCAN) &&
                context.hasPermission(Manifest.permission.BLUETOOTH_CONNECT)
        } else true
    }

    private fun isLocationPermissionGranted(context: Context): Boolean {
        return context.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

//    private fun isStoragePermissionGranted(context: Context): Boolean {
//        return context.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) &&
//            context.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    }
}