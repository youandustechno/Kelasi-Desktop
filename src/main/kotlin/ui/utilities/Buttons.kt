package ui.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.videos.VideoState

@Composable
fun LoginButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.fillMaxWidth()
                .defaultMinSize(minWidth = 50.dp, minHeight = 30.dp )
                //.background(Color.Green)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}

@Composable
fun TabButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.width(150.dp)
                .defaultMinSize(minWidth = 50.dp, minHeight = 10.dp )
                .background(Color(0XFF5ACA0))
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun LinkButton(value: String, color: Color = Color(0XFF4A3125), click: () -> Unit) {
    Box(modifier = Modifier
                .wrapContentWidth()
                .defaultMinSize(minWidth = 30.dp, minHeight = 10.dp )
                .background(color, shape = RoundedCornerShape(10.dp))
        .clickable {
            click()
        }) {
            Text(value, color =Color.White,
                style =  MaterialTheme.typography.caption,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 9.dp, end = 9.dp, top = 5.dp, bottom = 5.dp))
    }
}

@Composable
fun NavigationButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.fillMaxWidth()
                .defaultMinSize(minWidth = 150.dp, minHeight = 30.dp )
                //.background(Color.Green)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}

@Composable
fun SubmitQuizButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.fillMaxWidth()
                .height(30.dp)
                .padding( top = 0.dp, bottom = 0.dp)) {
            Text(value, style =  MaterialTheme.typography.caption.copy(fontSize = 10.sp))
        }
    }
}

@Composable
fun PlayStopButton(videoState: VideoState, click: (VideoState) -> Unit) {

    Row {
        Button( { click.invoke(videoState)},
            modifier = Modifier.width(100.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(50.dp))
                .padding(5.dp)) {

            val res: String = when(videoState) {
                VideoState.RESUME -> "image/icon_pause.svg"
                VideoState.PAUSE -> "image/icon_play.svg"
                VideoState.START -> "image/icon_pause.svg"
                VideoState.STOP -> "image/icon_play.svg"
                VideoState.FORWARD -> "image/icon_forward.svg"
                VideoState.REWIND -> "image/icon_rewind.svg"
                else -> "image/icon_play.svg"
            }

            ResourceImageController30by30(res) {
            }
        }
    }
}


@Composable
fun ConfirmButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.width(300.dp)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}

@Composable
fun ManageDeleteButton(value: String, click: () -> Unit) {
    Row {
        Button( { click()},
            modifier = Modifier.width(450.dp)
                //.background(Color.Green)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}

@Composable
fun DenyButton(value: String, click: () -> Unit) {
    Row {
        Button( { click()},
            modifier = Modifier.width(150.dp)
                //.background(Color.Green)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}