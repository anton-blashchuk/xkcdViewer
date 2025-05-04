package com.ablashchuk.xkcdviewer.ui.view


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ablashchuk.xkcdviewer.ui.viewmodel.MainViewModel
import me.saket.telephoto.zoomable.ZoomSpec
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage
import me.saket.telephoto.zoomable.rememberZoomableImageState
import me.saket.telephoto.zoomable.rememberZoomableState

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val imageUrl by viewModel.comic.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            SimpleButton(
                text = "|<",
                onClick = { viewModel.first() },
                enabled = viewModel.firstAvailable()
            )
            SimpleButton(
                text = "<Prev",
                onClick = { viewModel.previous() },
                enabled = viewModel.previousAvailable()
            )
            SimpleButton(
                text = "Random",
                onClick = { viewModel.random() },
                enabled = viewModel.randomAvailable()
            )
            SimpleButton(
                text = "Next>",
                onClick = { viewModel.next() },
                enabled = viewModel.nextAvailable()
            )
            SimpleButton(
                text = ">|",
                onClick = { viewModel.last() },
                enabled = viewModel.lastAvailable()
            )
        }

        imageUrl?.let {
            val zoomableState = rememberZoomableState(
                zoomSpec = ZoomSpec(minZoomFactor = 1f, maxZoomFactor = 4f)
            )
            ZoomableAsyncImage(
                model = it.img,
                contentDescription = "Comic",
                state = rememberZoomableImageState(zoomableState),
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp)

            )
        } ?: Text("Loading or failed to load image")
    }
}

@Composable
fun SimpleButton(
    modifier: Modifier = Modifier, text: String, onClick: () -> Unit = {}, enabled: Boolean = true
) {
    Button(
        modifier = Modifier
            .padding(4.dp)
            .then(modifier),
        contentPadding = PaddingValues(4.dp),
        onClick = onClick,
        enabled = enabled) {
        Text(
            modifier = Modifier
                .padding(2.dp)
                .then(modifier),
            text = text,
            maxLines = 1,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonsRowPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            SimpleButton(
                text = "|<",
            )
            SimpleButton(
                text = "<Prev",
            )
            SimpleButton(
                text = "Random",
            )
            SimpleButton(
                text = "Next>",
            )
            SimpleButton(
                text = ">|",
            )
        }
    }
}