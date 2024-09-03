package ui.manages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.video.VideoComponent
import ui.NavHelper
import ui.utilities.*
import ui.utilities.FieldsValidation.isValid

@Composable
fun ManageVideos( navHelper: NavHelper) {

    val coroutineScope = rememberCoroutineScope()

    //var playerState by remember { mutableStateOf(VideoState.NONE) }
    var fileToUpload by remember { mutableStateOf<String?>(null) }
    val manageViewModel = ManageViewModel()
    var currentVideoComponent: VideoComponent? by remember { mutableStateOf(null) }
    var moduleId by remember { mutableStateOf("") }
    var courseId by remember { mutableStateOf("") }

    var instructorName : String by remember { mutableStateOf("") }
    var courseName : String by remember { mutableStateOf("") }
    var courseDescription : String by remember { mutableStateOf("") }
    var moduleName : String by remember { mutableStateOf("") }
    var videoTitle : String by remember { mutableStateOf("") }
    var videoDescription : String by remember { mutableStateOf("") }
    var error: Boolean? by remember { mutableStateOf(null) }
    var action: String by remember { mutableStateOf("") }


    if(navHelper.dataMap.containsKey("action")) {
        if(navHelper.dataMap["action"].toString() == "video") {
            // add new video in current module
            instructorName = navHelper.dataMap["instructor"].toString()
            courseId = navHelper.dataMap["courseId"].toString()
            moduleId = navHelper.dataMap["moduleId"].toString()
            courseName = navHelper.dataMap["courseName"].toString()
            courseDescription = navHelper.dataMap["description"].toString()
            moduleName = navHelper.dataMap["moduleName"].toString()
            action = navHelper.dataMap["action"].toString()

        } else if(navHelper.dataMap["action"].toString() == "update") {
            //update
            currentVideoComponent = navHelper.dataMap["video"] as VideoComponent
            moduleId = navHelper.dataMap["moduleId"].toString()
            courseId = navHelper.dataMap["courseId"].toString()
            instructorName = navHelper.dataMap["instructor"].toString()
            courseName = navHelper.dataMap["courseName"].toString()
            courseDescription = navHelper.dataMap["description"].toString()
            moduleName = navHelper.dataMap["moduleName"].toString()
            videoTitle = currentVideoComponent?.title?:""
            videoDescription = currentVideoComponent?.description?:""
            action = navHelper.dataMap["action"].toString()

        } else if(navHelper.dataMap["action"].toString() == "add"){
            //add module and new video
            instructorName = navHelper.dataMap["instructor"].toString()
            courseName = navHelper.dataMap["courseName"].toString()
            courseDescription = navHelper.dataMap["description"].toString()
            courseId = navHelper.dataMap["courseId"].toString()
            action = navHelper.dataMap["action"].toString()
        }
    }

//    LazyColumn(Modifier.fillMaxSize()) {
//        item {
//            Column(
//                Modifier.padding(top=30.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
//                    //Player Part
//                    Column(Modifier.fillMaxWidth(.7F)
//                        .wrapContentHeight()
//                        .heightIn(600.dp, 800.dp)
//                        .padding(20.dp)
//                        .background(Color(0XFFf7f7f7)),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Top) {
//
//                        Column(
//                            Modifier
//                                .defaultMinSize(minWidth = 700.dp, minHeight = 300.dp)
//                                .fillMaxWidth()
//                                .heightIn(300.dp, 400.dp)
//                                .padding(15.dp)
//                        ) {
//                            if(currentVideoComponent != null) {
//                                VideoPlayerCards(
//                                    currentVideoComponent!!.apply {
//                                        this.playerState = playerState
//                                    }
//                                )
//                            } else {
//                                VideoCard {
//                                    Text("Video No Available", style = MaterialTheme.typography
//                                        .button
//                                        .copy(color = Color.White))
//                                }
//                            }
//                        }
//                        val buttonText = when(playerState) {
//                            VideoState.NONE -> "Play"
//                            VideoState.PAUSE-> "Play"
//                            VideoState.START -> "Stop"
//                        }
//
//                        Row {
//                            ButtonContainer{
//                                PlayStopButton(buttonText) {
//                                    playerState = if(playerState != VideoState.START) VideoState.START
//                                    else VideoState.PAUSE
//                                }
//                            }
//                        }
//
//                        Spacer(Modifier.height(50.dp))
//
//                    }
//                    //Fields
//                    Column(Modifier.wrapContentWidth()
//                        .wrapContentSize()
//                        .padding(top = 30.dp),
//                        horizontalAlignment = Alignment.Start,
//                        verticalArrangement = Arrangement.Center) {
//
//                        if(!courseId.isValid()) {
//                            FieldContainer(if(!instructorName.isValid()) "Instructor Name" else "") {
//                                InstructorFields(instructorName) {
//                                    instructorName = it
//                                }
//                            }
//
//                            FieldContainer(if(!courseName.isValid()) "Course Name is empty" else "") {
//                                CourseFields(courseName) {
//                                    courseName = it
//                                }
//                            }
//
//                            FieldContainer(if(!courseDescription.isValid()) "Course Description" else "") {
//                                CourseDescriptionFields(courseDescription) {
//                                    courseDescription = it
//                                }
//                            }
//
//                        } else {
//
//                            Column(
//                                Modifier.padding(end = 30.dp),
//                                horizontalAlignment = Alignment.Start,
//                                verticalArrangement = Arrangement.Center) {
//                                if(instructorName.isValid()){
//                                    Text("Instructor", style = MaterialTheme.typography.subtitle2.copy(Color.DarkGray))
//                                    Spacer(Modifier.height(3.dp))
//                                    Text(instructorName, style= MaterialTheme.typography.body2)
//                                    Spacer(Modifier.height(10.dp))
//                                }
//
//                                if(courseName.isValid()) {
//                                    Text("Course Name", style = MaterialTheme.typography.subtitle2.copy(Color.DarkGray))
//                                    Spacer(Modifier.height(3.dp))
//                                    Text(courseName, style= MaterialTheme.typography.body2)
//                                    Spacer(Modifier.height(10.dp))
//                                }
//                                if(courseDescription.isValid()){
//                                    Text("Course Description", style = MaterialTheme.typography.subtitle2.copy(Color.DarkGray))
//                                    Spacer(Modifier.height(3.dp))
//                                    Text(courseDescription, style = MaterialTheme.typography.body2)
//                                }
//
//                            }
//                            Spacer(Modifier.height(20.dp))
//                        }
//
//
//                        FieldContainer(if(!moduleName.isValid())"Module is empty" else "") {
//                            ModuleFields(moduleName) {
//                                moduleName = it
//                            }
//                        }
//
//                        FieldContainer(if(!videoTitle.isValid())"Video Title is empty" else "") {
//                            VideoTitleFields(videoTitle) {
//                                videoTitle = it
//                            }
//
//                            if(videoTitle.isValid() && currentVideoComponent != null) {
//                                currentVideoComponent!!.title = videoTitle
//                            }
//                        }
//
//                        FieldContainer(if(!videoDescription.isValid())"Video Description is empty" else "") {
//                            VideoDescriptionFields(videoDescription) {
//                                videoDescription = it
//                            }
//                            if(videoDescription.isValid() && currentVideoComponent != null) {
//                                currentVideoComponent!!.description = videoDescription
//                            }
//                        }
//
//                        if(!fileToUpload.isNullOrEmpty()) {
//                            Spacer(Modifier.height(10.dp))
//                        }
//                        Row(Modifier.width(500.dp),
//                            horizontalArrangement = Arrangement.Start,
//                            verticalAlignment = Alignment.CenterVertically) {
//
//                            ResourceImage50by50("image/upload.png") {
//                                fileToUpload = showFileChooser()
//                            }
//
//                            if(!fileToUpload.isNullOrEmpty()) {
//                                fileToUpload?.let {
//                                    manageViewModel.selectFile(it)
//                                    Text(it)
//                                }
//                            } else {
//                                Box(Modifier.clickable {
//                                    fileToUpload = showFileChooser()
//                                }){
//                                    Text("Upload Video")
//                                }
//                            }
//                        }
//
//                        if(!fileToUpload.isNullOrEmpty()) {
//                            Spacer(Modifier.height(10.dp))
//                        }
//
//                        Row (Modifier.width(500.dp),
//                            horizontalArrangement = Arrangement.Start) {
//                            if(!fileToUpload.isNullOrEmpty()) {
//                                ConfirmButton("Upload") {
//
//                                    if(!courseName.isValid() && currentVideoComponent == null) {
//                                        error = true
//                                    } else if( !moduleName.isValid() && currentVideoComponent == null) {
//                                        error = true
//                                    } else if( !instructorName.isValid()) {
//                                        error = true
//                                    } else if( !videoTitle.isValid()) {
//                                        error = true
//                                    } else if( !videoDescription.isValid()) {
//                                        error = true
//                                    } else {
//                                        coroutineScope.launch(Dispatchers.IO) {
//
//                                            val videoResult = if(action == "update") {
//                                                //update video in existing module and class
//                                                manageViewModel
//                                                    .uploadFile(
//                                                        courseId,
//                                                        moduleId,
//                                                        videoTitle,
//                                                        videoDescription,
//                                                        currentVideoComponent)
//
//                                            } else  if(action == "add") {
//                                                //add new module and new video file
//                                                manageViewModel
//                                                    .addVideoFileAndModule(
//                                                        courseId,
//                                                        moduleName,
//                                                        videoTitle,
//                                                        videoDescription)
//
//                                            } else  if(action == "video") {
//                                                //add a new video file in existing module
//                                                manageViewModel
//                                                    .addNewVideoInModule(
//                                                        courseId,
//                                                        moduleId,
//                                                        videoTitle,
//                                                        videoDescription)
//                                            }
//                                            else  {
//                                                //create Course, module and video
//                                                manageViewModel
//                                                    .createTopic(
//                                                        instructorName,
//                                                        courseName,
//                                                        courseDescription,
//                                                        moduleName,
//                                                        videoTitle,
//                                                        videoDescription)
//                                            }
//
//                                            withContext(Dispatchers.Main) {
//                                                if(videoResult?.uploaded != null) {
//                                                    currentVideoComponent = videoResult.uploaded
//                                                } else {
//                                                    //Todo
//                                                    //currentVideo = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                Row(Modifier.padding(top = 40.dp)) {
//                    ButtonContainer{
//                        ManageDeleteButton("Delete"){
//                            // Todo delete video
//                        }
//                    }
//                }
//            }
//        }
//    }
}

//enum class VideoState { START, PAUSE, NONE}