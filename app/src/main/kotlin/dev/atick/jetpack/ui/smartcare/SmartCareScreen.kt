package dev.atick.jetpack.ui.smartcare

import android.animation.ValueAnimator
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieAnimationView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import dev.atick.jetpack.R
import dev.atick.jetpack.ui.MainViewModel
import dev.atick.jetpack.ui.destinations.IdScreenDestination
import dev.atick.jetpack.ui.destinations.UploadScreenDestination


@Composable
@Destination
//@RootNavGraph(start = true)
fun SmartCareScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {

    val smartCareUiState by viewModel.smartCareUiState.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(32.dp)) {
            Text(
                text = "Record Data from Smart Care",
                fontWeight = FontWeight.Thin,
                fontSize = 56.sp,
                lineHeight = 64.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Heart Rate",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${smartCareUiState.heartRate}",
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Thin,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "BPM",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                AndroidView(
                    factory = { ctx ->
                        LottieAnimationView(ctx).apply {
                            setAnimation(R.raw.heart_beat)
                            repeatCount = ValueAnimator.INFINITE
                            speed = 2.0F
                            playAnimation()
                        }
                    },
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(0.4F)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = smartCareUiState.smartCareState.description,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (smartCareUiState.smartCareState == SmartCareState.RecordingComplete) {
                    navigator.navigate(UploadScreenDestination) {
                        popUpTo(IdScreenDestination) {
                            inclusive = false
                        }
                    }
                } else {
                    viewModel.toggleConnection()
                }
            }) {
                Text(
                    text = if (smartCareUiState.smartCareState == SmartCareState.RecordingComplete)
                        "Next"
                    else if (smartCareUiState.smartCareState != SmartCareState.Disconnected)
                        "Cancel"
                    else "Record"
                )
            }

//            Button(onClick = {
//                viewModel.saveRecording()
//                navigator.navigate(UploadScreenDestination) {
//                    popUpTo(IdScreenDestination) {
//                        inclusive = false
//                    }
//                }
//            }) {
//                Text(text = "Next")
//            }
        }

        Image(
            modifier = Modifier
                .width(180.dp)
                .height(240.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.plant3),
            contentDescription = "plant"
        )
    }
}