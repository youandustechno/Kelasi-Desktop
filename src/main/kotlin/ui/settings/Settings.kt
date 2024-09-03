package ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.CoursesListResponse
import models.video.CourseComponent
import models.video.VideoComponent
import ui.NavHelper
import ui.utilities.ConfirmButton
import ui.utilities.FieldsValidation.isValid
import ui.utilities.ResourceImage50by50
import ui.utilities.VideoImageUrl
import ui.utilities.showFileChooser
import ui.videos.VideosViewModel

@Composable
fun Settings(
    header: @Composable () -> Unit,
    coursesGlobalList: CoursesListResponse?,
    navhelper: NavHelper,
    courses: (CoursesListResponse?)-> Unit,
    onClick: (NavHelper) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val videosViewModel : VideosViewModel by remember { mutableStateOf(VideosViewModel()) }

    var fileToUpload by remember { mutableStateOf<String?>(null) }

    val videosList: MutableList<VideoComponent> by remember { mutableStateOf(mutableListOf()) }

    var course : CourseComponent? by remember { mutableStateOf(null) }

    var courseUrl : String? by remember { mutableStateOf(null) }

    var state by remember { mutableStateOf(false) }

    if (navhelper.dataMap.isNotEmpty() && navhelper.dataMap.containsKey("course")) {
        course = navhelper.dataMap.get("course") as CourseComponent
        course?.modules?.forEach { module ->
            videosList.addAll(module.videos)
        }
        //videosList.addAll(course.modules[0].videos)
        state = true
    } else  if(navhelper.dataMap.containsKey("courseId")) {

        LaunchedEffect(Unit) {
            courses(videosViewModel.getCoursesList())
        }

        val id = navhelper.dataMap["courseId"]
        course = coursesGlobalList?.courses?.firstOrNull { it._id == id } as CourseComponent
        videosList.clear()
        course?.modules?.forEach { module ->
            if(module.videos.isNotEmpty()) {
                videosList.addAll(module.videos)
            }
        }
        state = true
    }
    else {
        //go to courses
    }

    Column {
        Row(
            Modifier.fillMaxWidth().padding(end = 20.dp),
            horizontalArrangement = Arrangement.End) {
            Box(Modifier.wrapContentSize()) {
                ResourceImage50by50("image/upload.png") {
                    fileToUpload = showFileChooser()
                }
            }
            if(!fileToUpload.isNullOrEmpty()) {
                fileToUpload?.let {
                    videosViewModel.selectImageFile(it)
                    Text(it)

                    ConfirmButton("Upload") {
                        if(it.isValid()) {
                            coroutineScope.launch(Dispatchers.IO) {
                                val videoResult = course?._id?.let { courseId ->
                                    videosViewModel.uploadImageFile(courseId)
                                }

                                withContext(Dispatchers.Main) {
                                    if(videoResult?.updatedTopicUrl?.isValid() == true) {
                                        courseUrl= videoResult.updatedTopicUrl
                                    } else {
                                        //Todo
                                        //currentVideo = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Row {
            if(courseUrl?.isValid() == true) {
                VideoImageUrl(courseUrl!!)
            }
        }
    }
}