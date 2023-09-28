package dev.atick.jetpack.ui.upload

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.atick.jetpack.R
import dev.atick.jetpack.ui.MainViewModel
import dev.atick.jetpack.ui.destinations.IdScreenDestination

@Composable
@Destination
fun UploadScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {

    val uploadUiState by viewModel.uploadUiState.collectAsState()

//    val imageUris = remember { viewModel.getImageUris() }
//    val recordingUri = remember { viewModel.getRecordingUri() }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(32.dp)) {
            Text(
                text = "Upload Data to the HemaCare Server",
                fontWeight = FontWeight.Thin,
                fontSize = 56.sp,
                lineHeight = 64.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(32.dp))

//            Text(
//                text = "Selected Images:\n${imageUris}\n\n" +
//                    "Selected Recording:\n${recordingUri}",
//                color = MaterialTheme.colorScheme.onSurface
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (uploadUiState.uploadState == UploadState.UploadComplete) {
                        navigator.navigate(IdScreenDestination)
                    } else {
                        viewModel.upload()
                    }
                },
                enabled = uploadUiState.uploadState != UploadState.Uploading
            ) {
                Icon(Icons.Default.Upload, contentDescription = "Upload")
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = uploadUiState.uploadState.description)
            }
        }

        Image(
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.plant),
            contentDescription = "upload"
        )
    }
}