package ui.utilities

import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FilenameFilter

//fun showFileChooser(): String? {
//    val fileDialog = FileDialog(Frame(), "Select File", FileDialog.LOAD)
//    fileDialog.isVisible = true
//    val file = fileDialog.file
//    return if (file != null) {
//        File(fileDialog.directory, file).absolutePath
//    } else {
//        null
//    }
//}

fun showImageFileChooser(): String? {
    val fileDialog = FileDialog(Frame(), "Select File", FileDialog.LOAD)
    fileDialog.filenameFilter = FilenameFilter {dir, name ->
        val file = File(dir, name)
        file.isFile &&
                name.endsWith(".png", true) ||
                name.endsWith(".gif", true) ||
                name.endsWith(".bmp", true) ||
                name.endsWith(".webp", true) ||
                name.endsWith(".svg", true) ||
                name.endsWith(".jpeg", true)
    }
    fileDialog.isVisible = true
    val file = fileDialog.file
    return if (file != null) {
        File(fileDialog.directory, file).absolutePath
    } else {
        null
    }
}