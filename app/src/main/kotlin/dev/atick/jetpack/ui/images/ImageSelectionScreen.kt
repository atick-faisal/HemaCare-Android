package dev.atick.jetpack.ui.images

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import dev.atick.jetpack.R
import dev.atick.jetpack.ui.MainViewModel
import dev.atick.jetpack.ui.destinations.IdScreenDestination
import dev.atick.jetpack.ui.destinations.SmartCareScreenDestination
import timber.log.Timber
import java.io.File

@Composable
@Destination
fun ImageSelectionScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    val imageSelectionUiState by viewModel.imageSelectionUiState.collectAsState()

    val context = LocalContext.current
    val images = remember { mutableStateListOf<Bitmap>() }


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(5)
    ) { uris ->
        viewModel.setSelectedImages(uris)
        uris.forEach { uri ->
            uri.path?.let { Timber.d("SKYNET", "${File(it)}") }
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }

            bitmap?.let { image ->
                images.add(image)
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(32.dp)) {
            Text(
                text = "Select Images from the Gallery",
                fontWeight = FontWeight.Thin,
                fontSize = 56.sp,
                lineHeight = 64.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(visible = imageSelectionUiState.imageUris.isNotEmpty()) {
                LazyRow(Modifier.height(200.dp)) {
                    items(images) { image ->
                        Image(bitmap = image.asImageBitmap(), contentDescription = "Image")
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                if (imageSelectionUiState.imageUris.isEmpty()) {
                    launcher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                } else {
                    navigator.navigate(SmartCareScreenDestination) {
                        popUpTo(IdScreenDestination) {
                            inclusive = false
                        }
                    }
                }
            }) {
                Text(
                    text = if (imageSelectionUiState.imageUris.isEmpty())
                        "Open Gallery"
                    else "Next"
                )
            }
        }

        Image(
            modifier = Modifier
                .width(240.dp)
                .height(160.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.plant2),
            contentDescription = "plant"
        )
    }
}