package dev.atick.jetpack.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import dev.atick.jetpack.ui.theme.JetpackTheme
import dev.atick.jetpack.utils.PermissionUtils
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionUtils: PermissionUtils

    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            JetpackTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }

        permissionUtils.initialize(this) {
            Timber.i("ALL PERMISSION GRANTED!")
        }
    }

    override fun onResume() {
        permissionUtils.setupBluetooth(this)
        super.onResume()
        Timber.i("ON RESUME CALLED ${i++}")
    }
}