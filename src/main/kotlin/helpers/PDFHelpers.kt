package helpers

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import org.apache.pdfbox.text.PDFTextStripper
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import javax.imageio.ImageIO

val createdFiles = mutableListOf<File>()

suspend fun downloadPdfFromUrl(pdfUrl: String): File? {

    return try {
        val url = URL(pdfUrl)
        val destination = createUniqueTempFile("downloaded_pdf")

        url.openStream().use { input ->
            Files.copy(input, destination.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
        destination

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun createUniqueTempFile(prefix: String, suffix: String = ".pdf"): File {
    val uniqueName = "${prefix}_${UUID.randomUUID()}"

    return File.createTempFile(uniqueName, suffix)
}

fun RenderPDFToImages(pdfFile: File): List<BufferedImage> {

    try {
        val document = PDDocument.load(pdfFile)
        val pdfRenderer = PDFRenderer(document)
        val pages = mutableListOf<BufferedImage>()

        for (i in 0 until document.numberOfPages) {
            val pageImage = pdfRenderer.renderImageWithDPI(i, 300F)
            pages.add(pageImage)
        }

        return pages
    } catch (e:Exception) {
        return  mutableListOf<BufferedImage>()
    }
}

fun BufferedImage.toComposeImageBitmap() : ImageBitmap {
    val baos = ByteArrayOutputStream()
    ImageIO.write(this, "png", baos)
    val byteArray = baos.toByteArray()

    val skiaImage = org.jetbrains.skia.Image.makeFromEncoded(byteArray)

    return  skiaImage.toComposeImageBitmap()
}

fun ImageIO.writeToByteArray(image: BufferedImage): ByteArray {
    return ByteArrayOutputStream().use { output ->
        ImageIO.write(image, "png", output)
        output.toByteArray()
    }
}

fun extractTextFromPdf(pdfFilePath: String): String {

    try {
        val file = File(pdfFilePath)
        PDDocument.load(file).use {document ->
            val pdfStripper = PDFTextStripper()
            return pdfStripper.getText(document)
        }
    } catch (e:Exception){
        return ""
    }
}

fun deleteCreatedFiles() {
    createdFiles.forEach { file ->
        if(file.exists()) {
            file.delete()
        }
    }
    createdFiles.clear()
}