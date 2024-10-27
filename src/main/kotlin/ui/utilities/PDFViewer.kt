package ui.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.graphics.toComposeImageBitmap
import helpers.toComposeImageBitmap
import ui.NavKeys.EMPTY
import java.awt.image.BufferedImage

@Composable
fun PDFViewer(pdfImages: List<BufferedImage>) {
    Column(Modifier
        .background(Color.LightGray)
        .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 5.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        pdfImages.forEachIndexed { index, image ->
            println(EMPTY+index)
            val bitmap = image.toComposeImageBitmap()
            Column (Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp)
                .background(Color.LightGray)) {

                Image(bitmap = bitmap, contentDescription = "document pdf")
                Spacer(Modifier)
            }
        }
    }
}