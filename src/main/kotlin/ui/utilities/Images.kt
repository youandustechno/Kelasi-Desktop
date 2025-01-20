package ui.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image
import ui.videos.VideoState
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

private val imageDefault = "https://fastly.picsum.photos/id/883/200/200.jpg?hmac=evNCTcW3jHI_xOnAn7LKuFH_YkA8r6WdQovmsyoM1IY"


@Composable
fun UserImageUrl(image: String = imageDefault, onClick:(() ->Unit)? = null) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageBitmap) {
        imageBitmap = loadNetworkImage(image)
    }
    imageBitmap?.let {
        Image(bitmap = it,
            contentDescription = "video image",
            modifier = Modifier
                .width(145.dp)
                .height(145.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .clickable {
                    onClick?.invoke()
                },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun UserFileImageBitMap(imageBitmap: ImageBitmap,onClick:(() ->Unit)? = null ) {
    Image(bitmap = imageBitmap,
        contentDescription = "video image",
        modifier = Modifier
            .width(145.dp)
            .height(145.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
            .clickable {
                onClick?.invoke()
            },
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ResourceUserImage(imagePath: String, onClick:(() ->Unit)? = null) {
    //""
    Image(
        painter = painterResource(imagePath),
        contentDescription = "people",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(145.dp)
            .height(145.dp)
            .clip(CircleShape)
            .padding(7.dp)
            .background(Color.White)
            .border(2.dp, Color.Transparent)
            .clickable {
                onClick?.invoke()
                //fileToUpload = showFileChooser()
            }
    )
}

@Composable
fun ResourceImageController30by30(res: String, onClick:(() ->Unit)? = null) {
    //""
    Image(
        painter = painterResource(res),
        contentDescription = "people",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .padding(5.dp)
            .clickable {
                onClick?.invoke()
                //fileToUpload = showFileChooser()
            }
    )
}

@Composable
fun UserBackgroundImageUrl(image: String = imageDefault, onClick:(() ->Unit)? = null) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageBitmap) {
        imageBitmap = loadNetworkImage(image)
    }
    imageBitmap?.let {
        Image(bitmap = it,
            contentDescription = "video image",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .clickable {
                    onClick?.invoke()
                },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun UserPictureButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.width(135.dp)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.caption
                .copy(fontSize = 11.sp,
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    lineHeight = 20.sp
                )
            )
        }
    }
}


@Composable
fun CouseImageUrl(image: String = imageDefault) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageBitmap) {
        imageBitmap = loadNetworkImage(image)
    }
    Box(Modifier.height(100.dp)
        .width(200.dp)
        .padding(2.dp)) {
        imageBitmap?.let {
            Image(bitmap = it,
                contentScale = ContentScale.Crop,
                contentDescription = "video image",
                modifier = Modifier
                    .width(196.dp)
                    .height(96.dp))
        }
    }
}

@Composable
fun PlayStopImageButton(videoState: VideoState, click: () -> Unit) {
    val res: String = when (videoState) {
        VideoState.PAUSE -> "image/icon_play.svg"
        VideoState.START -> "image/icon_pause.svg"
        VideoState.REWIND -> "image/icon_pause.svg"
        VideoState.FORWARD -> "image/icon_pause.svg"
        VideoState.INIT -> "image/icon_pause.svg"
        else -> "image/icon_play.svg"
    }

    Box(modifier = Modifier
        .clip(CircleShape)
        .size(75.dp),
        contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(res),
            contentDescription = "play or pause button",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .clipToBounds()
                .clickable {
                    click.invoke()
                }
        )
    }
}

@Composable
 fun VideoImageUrl(image: String = imageDefault) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageBitmap) {
        imageBitmap = loadNetworkImage(image)
    }
    imageBitmap?.let {
        Image(bitmap = it,
        contentDescription = "video image",
        modifier = Modifier.fillMaxWidth())
    }
}

private suspend fun loadNetworkImage(link: String): ImageBitmap? = withContext(Dispatchers.IO) {

    try {
        val url = URL(link)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = connection.inputStream
        val bufferedImage = ImageIO.read(inputStream)

        val stream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", stream)
        val byteArray = stream.toByteArray()

        Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    }catch (exc: Exception) {
        null
    }
}

@Composable
fun ResourceImageVideo50by50(imagePath: String, onClick:(() ->Unit)? = null) {
    //""
    Image(
        painter = painterResource(imagePath),
        contentDescription = "people",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(50.dp)
            .clickable {
                onClick?.invoke()
                //fileToUpload = showFileChooser()
            }
    )
}

@Composable
fun ResourceImage50by50(imagePath: String, onClick:(() ->Unit)? = null) {
    //""
    Image(
        painter = painterResource(imagePath),
        contentDescription = "people",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .padding(5.dp)
            .clickable {
                onClick?.invoke()
                //fileToUpload = showFileChooser()
            }
    )
}

@Composable
fun ResourceImage30by30(imagePath: String, onClick:(() ->Unit)? = null) {
    //""
    Image(
        painter = painterResource(imagePath),
        contentDescription = "people",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .padding(5.dp)
            .clickable {
                onClick?.invoke()
                //fileToUpload = showFileChooser()
            }
    )
}

@Composable
fun ResourceImageDashboard(imagePath: String, onClick:(() ->Unit)? = null) {
    //""
    Image(
        painter = painterResource(imagePath),
        contentDescription = "people",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .width(200.dp)
            .height(100.dp)
            .padding(2.dp)
            .clickable {
                onClick?.invoke()
                //fileToUpload = showFileChooser()
            }
    )
}


fun loadImageBitmap(file: File): ImageBitmap? {
    return file.inputStream().buffered().use {
        Image.makeFromEncoded(it.readBytes()).toComposeImageBitmap()
    }
}