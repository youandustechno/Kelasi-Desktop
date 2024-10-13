package ui.videos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import helpers.RenderPDFToImages
import helpers.downloadPdfFromUrl
import java.awt.image.BufferedImage
import java.io.File

class MangeDocViewModel {


    var documentPath by mutableStateOf<String?>(null)
        private set

    suspend fun getDocumentUrl(url: String) :  List<BufferedImage>? {
        val downloadedFile = downloadPdfFromUrl(url)
        return if(downloadedFile != null) {
            RenderPDFToImages(downloadedFile)
        } else {
            null
        }
    }
}