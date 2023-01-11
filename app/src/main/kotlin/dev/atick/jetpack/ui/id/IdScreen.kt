package dev.atick.jetpack.ui.id

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.atick.jetpack.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
//@RootNavGraph(start = true)
fun IdScreen(
    navigator: DestinationsNavigator
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(32.dp)) {
            Text(
                text = "Give the Patient an Unique ID",
                fontWeight = FontWeight.Thin,
                fontSize = 56.sp,
                lineHeight = 64.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = "For Example: 001")
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Next")
            }
        }

        Image(
            modifier = Modifier
                .width(240.dp)
                .height(360.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.plant),
            contentDescription = "plant"
        )
    }
}