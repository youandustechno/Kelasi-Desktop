package ui.utilities

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
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

    val factory = remember { { mediaPlayerComponent } }

    if(videoComponent.playerState == VideoState.PAUSE) {
         mediaPlayer.media().startPaused(videoComponent.url)
    }

    if (videoComponent.playerState == VideoState.START) {
        //mediaPlayer.media().startPaused(videoComponent.url )
        mediaPlayer.media().play(videoComponent.url )
    }

    DisposableEffect(Unit) { onDispose(mediaPlayer::release) }
    SwingPanel(
        factory = factory,
        background = Color.Transparent,
        modifier = modifier,
        update = {

        }
    )
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