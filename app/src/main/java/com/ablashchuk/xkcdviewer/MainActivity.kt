package com.ablashchuk.xkcdviewer

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ablashchuk.xkcdviewer.ui.theme.XkcdViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XkcdViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Row {
                            PlainButton(
                                text = "|<",
                                onClick =
                                stubOnClickListener("|<", this@MainActivity),
                                modifier = Modifier.padding(innerPadding)
                            )
                            PlainButton(
                                text = "<Prev",
                                onClick =
                                stubOnClickListener("<Prev", this@MainActivity),
                                modifier = Modifier.padding(innerPadding)
                            )
                            PlainButton(
                                text = "Random",
                                onClick =
                                stubOnClickListener("Random", this@MainActivity),
                                modifier = Modifier.padding(innerPadding)
                            )
                            PlainButton(
                                text = "Next>",
                                onClick =
                                stubOnClickListener("Next>", this@MainActivity),
                                modifier = Modifier.padding(innerPadding)
                            )
                            PlainButton(
                                text = ">|",
                                onClick =
                                stubOnClickListener(">|", this@MainActivity),
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        var scale by remember { mutableFloatStateOf(1f) }
                        var offset by remember { mutableStateOf(Offset(0f, 0f)) }
                        Image(
                            painter = painterResource(id = R.drawable.sample_comic),
                            contentDescription = "main comic",
                            modifier = Modifier
                                .padding(innerPadding)
                                .pointerInput(Unit) {
                                    detectTransformGestures { _, pan, zoom, _ ->
                                        // Update the scale based on zoom gestures.
                                        scale *= zoom

                                        // Limit the zoom levels within a certain range (optional).
                                        scale = scale.coerceIn(1.0f, 5f)

                                        // Update the offset to implement panning when zoomed.
                                        offset = if (scale == 1f) Offset(0f, 0f) else offset + pan
                                    }
                                }
                                .clipToBounds()
                                //TODO 1. make zoom count in current offset
                                //TODO 2. limit offset to deny ability to pull empty space in view
                                .graphicsLayer(
                                    scaleX = scale, scaleY = scale,
                                    translationX = offset.x, translationY = offset.y,
                                )
                                .then(Modifier.fillMaxSize())
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun PlainButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick) {
        Text(
            text = text,
            maxLines = 1,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlainButtonPreview() {
    XkcdViewerTheme {
        PlainButton("Android is Android", onClick = {  })
    }
}

fun stubOnClickListener(text: CharSequence, context: Context): () -> Unit = {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()
}