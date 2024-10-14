package ui.videos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import helpers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.video.CourseComponent
import models.video.DocumentComponent
import models.video.Module
import ui.NavHelper
import ui.utilities.*
import java.awt.image.BufferedImage

@Composable
fun Documents(navHelper: NavHelper, onClick: (NavHelper) -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    var pdfImages by remember { mutableStateOf<List<BufferedImage>?>(null) }
    var textToRead by remember { mutableStateOf("") }
    var isMaxSize by remember { mutableStateOf(false) }
    var isReading: VoiceStates by remember { mutableStateOf(VoiceStates.NONE) }
    //var pdfListDocImages  = remember {  mutableListOf<BufferedImage>()>(null) }


    var course : CourseComponent? by remember { mutableStateOf(null) }
    var module : Module? by remember { mutableStateOf(null) }
    var documentToUpload by remember { mutableStateOf<String?>(null) }
    var docComponent: DocumentComponent? by remember { mutableStateOf(null) }
    var moduleId by remember { mutableStateOf("") }
    var courseId by remember { mutableStateOf("") }

    val uploadViewModel = MangeDocViewModel()

    var docTitle : String by remember { mutableStateOf("") }
    var docDescription : String by remember { mutableStateOf("") }
    var error: Boolean? by remember { mutableStateOf(null) }


    if (navHelper.dataMap.isNotEmpty() && navHelper.dataMap.containsKey("course")) {
        // courseMap["module"] = module
        //courseMap["course"] = course as CourseComponent
        course = navHelper.dataMap["course"] as CourseComponent
        course?._id?.let {
            courseId = it
        }
        if(module == null && navHelper.dataMap.containsKey("module")) {
            module = navHelper.dataMap["module"] as Module
            module?._id?.let {
                moduleId = it
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        if(module?.documents != null) {
            Row(
                Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, top = 10.dp)) {
                LazyRow {
                    items(module!!.documents) { document ->
                        DocCards({
                            coroutineScope.launch(Dispatchers.IO) {
                                val images = uploadViewModel.getDocumentUrl(document.url)
                                val downloadedFile = downloadPdfFromUrl(document.url)
                                var textToBeRead = ""
                                if (downloadedFile != null) {
                                    textToBeRead = extractTextFromPdf(downloadedFile.path)
                                }
                                withContext(Dispatchers.Main) {
                                    if(images != null) {
                                        docComponent = document
                                        docTitle = document.title
                                        docDescription = document.description
                                        pdfImages = images
                                        textToRead = textToBeRead
                                    }
                                }
                            }
                        }) {

                            Text(document.title)
                            Spacer(Modifier.height(5.dp))
                            Text(document.description)

                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }

            Spacer(Modifier.height(30.dp))
        }

        //Content below list of documents
        Row(Modifier.fillMaxSize()) {

            LazyColumn(
                Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1F)) {
                item {
                    Row(
                        Modifier
                            .then(if(isMaxSize) Modifier.padding(start = 30.dp, end = 30.dp) else Modifier
                                .padding(start = 150.dp, end = 150.dp))
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End) {

                        if(pdfImages?.isNotEmpty() == true) {
                            val iconState = if(isReading == VoiceStates.READING) {
                                "image/icon_volume_off.svg"
                            }else {
                                "image/icon_volume_high.svg"
                            }
                            ResourceImage50by50(iconState)  {
                                //to be replaced.
                                if(isReading == VoiceStates.READING) {
                                    stopReading()
                                    isReading = VoiceStates.NONE
                                } else if(isReading == VoiceStates.NONE) {
                                    if(isFrench(textToRead)) {
                                        readAloudFrench(textToRead)
                                    } else {
                                        readAloud(textToRead)
                                    }
                                    isReading = VoiceStates.READING
                                }
                                else {
                                    isReading = VoiceStates.NONE
                                }

                            }
                            Spacer(Modifier.width(30.dp))

                            Box(
                                Modifier
                                .then(if(isMaxSize) Modifier.width(50.dp).height(30.dp) else Modifier.size(30.dp))
                                .border(BorderStroke(3.dp, Color.DarkGray))
                                .clickable {
                                    isMaxSize = !isMaxSize
                                }) {}
                        }
                    }
                }
                item {
                    Column(Modifier
                        .then(if(isMaxSize) Modifier.padding(start = 30.dp, end = 30.dp) else Modifier
                            .padding(start = 150.dp, end = 150.dp))
                        .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        if(pdfImages?.isNotEmpty() == true) {
                            pdfImages?.let {
                                PDFViewer(it)
                            }
                        }
                        else if (pdfImages?.isEmpty() == true) {
                            Column (
                                Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {

                                Text("No file found", style = MaterialTheme.typography.caption)
                            }
                        } else {
                            Column {  }
                        }
                    }
                }
            }
        }
    }
}

enum class VoiceStates {
    READING,
    STOP,
    NONE
}