package ui.utilities

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import models.video.VideoComponent
import ui.videos.VideoState
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import java.awt.Component
import java.util.*


@Composable
fun VideoPlayerImpl(
    videoComponent: VideoComponent,
    modifier: Modifier = Modifier.fillMaxWidth()
        .fillMaxHeight(),
) {
    val mediaPlayerComponent = remember { initializeMediaPlayerComponent() }
    val mediaPlayer = remember { mediaPlayerComponent.mediaPlayer() }
    var isMediaLoaded by remember { mutableStateOf(false) }

    val factory = remember { { mediaPlayerComponent } }
    val skipTimeMillis = 10_000L

    var totalTime by remember { mutableStateOf(1L) }
    var currentTime by remember { mutableStateOf(0L) }
    var isUserSeeking by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            if (mediaPlayer.status().isPlaying) {
                currentTime = mediaPlayer.status().time()  // Get the current playback time
                totalTime = mediaPlayer.media().info().duration()  // Get total duration of the video
            }
            if(currentTime >= totalTime) {
                break
            }
            delay(300L)  // Poll every 500ms for updates
        }
    }

    when(videoComponent.playerState) {

        VideoState.START -> {
            if(isMediaLoaded && !mediaPlayer.status().isPlaying ) {
                mediaPlayer.controls().play()
            }
            else if(!isMediaLoaded) {
                mediaPlayer.media().play(videoComponent.url)
                isMediaLoaded = true
            }
        }
        VideoState.PAUSE -> {
            if(mediaPlayer.status().isPlaying) {
                mediaPlayer.controls().pause()
            }
        }
        VideoState.RESUME -> {
            if(!mediaPlayer.status().isPlaying) {
                mediaPlayer.controls().play()
            }
        }
        VideoState.STOP -> {
            mediaPlayer.controls().stop()
            isMediaLoaded = false
        }
        VideoState.REWIND -> {
            currentTime = mediaPlayer.status().time()
            val newTime = (currentTime - skipTimeMillis).coerceAtLeast(0)
            mediaPlayer.controls().setTime(newTime)
        }
        VideoState.FORWARD -> {
            currentTime = mediaPlayer.status().time()
            totalTime = mediaPlayer.media().info().duration()
            val newTime = (currentTime + skipTimeMillis).coerceAtMost(totalTime)
            mediaPlayer.controls().setTime(newTime)
        }
        else -> {}
    }

    DisposableEffect(Unit) { onDispose(mediaPlayer::release) }
    Row(Modifier
        .fillMaxWidth()
        .height(330.dp)){
        SwingPanel(
            factory = factory,
            background = Color.Transparent,
            modifier = modifier,
            update = {}
        )
    }

    Spacer(Modifier.height(15.dp))
    Row {
        Slider(
            value = (currentTime / totalTime.toFloat()).coerceIn(0f, 1f),  // Progress ratio
            onValueChange = { newValue ->
                // Seek the video to the new position when slider is dragged
                isUserSeeking = true
                mediaPlayer.controls().pause()
                val seekTime = (newValue * totalTime).toLong()
                mediaPlayer.controls().setTime(seekTime)
            },
            modifier = Modifier.fillMaxWidth(),
            //onValueChangeFinished = { isUserSeeking = false }
        )
    }
}

private fun initializeMediaPlayerComponent(): Component {
    NativeDiscovery().discover()
    return if (isMacOS()) {
        CallbackMediaPlayerComponent()
    } else {
        EmbeddedMediaPlayerComponent()
    }
}

private fun Component.mediaPlayer() = when (this) {
    is CallbackMediaPlayerComponent -> mediaPlayer()
    is EmbeddedMediaPlayerComponent -> mediaPlayer()
    else -> error("mediaPlayer() can only be called on vlcj player components")
}

private fun isMacOS(): Boolean {
    val os = System
        .getProperty("os.name", "generic")
        .lowercase(Locale.ENGLISH)
    return "mac" in os || "darwin" in os
}