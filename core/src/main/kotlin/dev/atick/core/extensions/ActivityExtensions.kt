package dev.atick.core.extensions

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

inline fun ComponentActivity.resultLauncher(
    crossinline onSuccess: () -> Unit
): ActivityResultLauncher<Intent> {
    val resultCallback = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == ComponentActivity.RESULT_OK) onSuccess.invoke()
        else finishAffinity()
    }
    return resultCallback
}

inline fun ComponentActivity.permissionLauncher(
    crossinline onSuccess: () -> Unit
): ActivityResultLauncher<Array<String>> {
    val resultCallback = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) onSuccess.invoke()
        else finishAffinity()
    }
    return resultCallback
}