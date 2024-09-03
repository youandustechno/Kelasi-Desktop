package ui.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

private val imageDefault = "https://fastly.picsum.photos/id/883/200/200.jpg?hmac=evNCTcW3jHI_xOnAn7LKuFH_YkA8r6WdQovmsyoM1IY"


@Composable
fun CouseImageUrl(image: String = imageDefault) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageBitmap) {
        imageBitmap = loadNetworkImage(image)
    }
    imageBitmap?.let {
        Image(bitmap = it,
        contentDescription = "video image",
        modifier = Modifier
            .width(200.dp)
            .height(100.dp))
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
