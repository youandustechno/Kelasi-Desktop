package ui.utilities

import java.awt.FileDialog
import java.awt.Frame
import java.io.File

fun showFileChooser(): String? {
    val fileDialog = FileDialog(Frame(), "Select File", FileDialog.LOAD)
    fileDialog.isVisible = true
    val file = fileDialog.file
    return if (file != null) {
        File(fileDialog.directory, file).absolutePath
    } else {
        null
    }
}