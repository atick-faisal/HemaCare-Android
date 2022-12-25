package dev.atick.core.extensions

import android.Manifest
import android.app.Notification
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.hasPermission(permissionType: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permissionType) ==
        PackageManager.PERMISSION_GRANTED
}

fun Context.showNotification(
    notificationId: Int,
    notification: Notification
) {
    if (hasPermission(Manifest.permission.POST_NOTIFICATIONS)) {
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, notification)
        }
    }
}