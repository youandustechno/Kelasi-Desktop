package ui.videos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.CoursesListResponse
import models.video.CourseComponent
import models.video.Module
import models.video.VideoComponent
import ui.NavHelper
import ui.NavKeys.COURSE
import ui.NavKeys.COURSE_ID
import ui.NavKeys.EMPTY
import ui.NavKeys.MODULE
import ui.Route
import ui.utilities.*


@Composable
fun Videos(
    coursesGlobalList: CoursesListResponse?,
    navhelper: NavHelper,
    courses: (CoursesListResponse?)-> Unit,
    onClick: (NavHelper) -> Unit
) {

   //val coroutineScope = rememberCoroutineScope()

    var playerState by remember { mutableStateOf(VideoState.NONE) }

    val videosViewModel : VideosViewModel  by remember { mutableStateOf(VideosViewModel()) }

    var state by remember { mutableStateOf(false) }

    //COURSE
    var course : CourseComponent? by remember { mutableStateOf(null) }

    //MODULE
    var selectedModule: Module? by remember { mutableStateOf(null) }
    var selectedModuleIndex: Int by remember { mutableStateOf(0) }

    //VIDEO
    val videosList: MutableList<VideoComponent> by remember { mutableStateOf(mutableListOf()) }
    var selectedVideo: VideoComponent? by remember { mutableStateOf(null) }
    var selectedVideoIndex: Int by remember { mutableStateOf(0) }

    //STATES
    var isFullSize : Boolean by remember { mutableStateOf(false) }
    var isInitial : Boolean by remember { mutableStateOf(true) }

    if (navhelper.dataMap.isNotEmpty() && navhelper.dataMap.containsKey(COURSE)) {
        course = navhelper.dataMap.get(COURSE) as CourseComponent
        course?.modules?.forEach { module ->
            videosList.addAll(module.videos)
        }
        //videosList.addAll(course.modules[0].videos)
        state = true
    } else  if(navhelper.dataMap.containsKey(COURSE_ID)) {

        LaunchedEffect(Unit) {
            courses(videosViewModel.getCoursesList())
        }

        val id = navhelper.dataMap[COURSE_ID]
        if( coursesGlobalList?.courses?.firstOrNull { it._id == id } != null) {
            course = coursesGlobalList.courses.firstOrNull { it._id == id } as CourseComponent
            videosList.clear()
            course?.modules?.forEach { module ->
                if(module.videos.isNotEmpty()) {
                    videosList.addAll(module.videos)
                }
            }
            state = true
        }
    }
    else {
        //go to courses
    }

    if(isInitial) {
        if(course?.modules != null && course?.modules!!.isNotEmpty() && selectedModule == null) {
            selectedModule = course!!.modules[0]
            if(course!!.modules[0].videos.isNotEmpty()) {
                selectedVideo = course!!.modules[0].videos[0]
            }
        }
    }

    Column(Modifier.fillMaxSize()) {

        if (state) {
            //Course Title
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {

                Box(Modifier.wrapContentSize()
                    .align(Alignment.Center)) {
                    Text(EMPTY+if(course != null) course?.name?.toUpperCase(Locale.current)
                    else EMPTY,
                        style = MaterialTheme.typography.h5)
                }

                if(selectedModule != null) {
                    Box (Modifier
                        .then(if(isFullSize) Modifier
                            .width(30.dp)
                            .height(20.dp)
                            .border(BorderStroke(3.dp, Color.DarkGray))
                        //.background(Color.Transparent, RoundedCornerShape(10.dp))
                        else Modifier
                            .width(40.dp)
                            .height(25.dp)
                            .border(BorderStroke(3.dp, Color.DarkGray))
                            // .background(Color.Transparent, RoundedCornerShape(10.dp))
                        )
                        .align(Alignment.CenterEnd)
                        .clickable {
                            isFullSize = ! isFullSize
                        }) {}
                }
            }

            if(!isFullSize && (course?.modules?.size?: 0) > 1) {
                //Module
                LazyRow(Modifier.fillMaxWidth()
                    .heightIn(50.dp, 70.dp)) {
                    if (course != null) {
                        itemsIndexed(course!!.modules) {  index, module ->
                            Row(Modifier
                                .wrapContentWidth()
                                .padding(start = 10.dp, end = 10.dp, top = 5.dp))
                            {
                                ModuleCard {
                                    Row(
                                        Modifier
                                            //.sizeIn(minWidth = 100.dp, minHeight = 120.dp, maxWidth = 120.dp, maxHeight = 150.dp)
                                            .then(if(index == selectedModuleIndex ) Modifier
                                                .background(Color.White, shape = RoundedCornerShape(5.dp))
                                                .border(1.dp, Color.DarkGray,shape = RoundedCornerShape(5.dp))
                                            else Modifier
                                                .background(Color.LightGray, shape = RoundedCornerShape(5.dp)))
                                            .clickable {
                                                selectedModule = module
                                                selectedModuleIndex = index
                                                playerState = VideoState.STOP
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(1000)
                                                    playerState = VideoState.NONE
                                                }
                                            }
                                            .padding(start = 10.dp, top= 5.dp, bottom = 5.dp, end = 10.dp)
                                            .fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(module.name, style = MaterialTheme.typography.caption
                                            .copy(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight(200),
                                                fontFamily = FontFamily.Serif,
                                                lineHeight = 24.sp
                                            ))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                if(selectedModule?.quiz?.isNotEmpty() == true) {
                    Row {
                        Box(Modifier.fillMaxWidth()
                            .heightIn(50.dp, 70.dp)) {

                            Row(Modifier.align(Alignment.CenterEnd)) {

                                LinkButton("Documentation") {
                                    val courseMap = mutableMapOf<String, Any>()
                                    courseMap[MODULE] = selectedModule!!
                                    courseMap[COURSE] = course as CourseComponent

                                    onClick.invoke(NavHelper(Route.ViewDocument, courseMap))
                                }

                                Spacer(Modifier.width(8.dp))

                                LinkButton("Take a Quiz") {
                                    val courseMap = mutableMapOf<String, Any>()
                                    courseMap[MODULE] = selectedModule!!
                                    courseMap[COURSE] = course as CourseComponent

                                    onClick.invoke(NavHelper(Route.Quiz, courseMap))
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                } else {
                    Row {
                        Box(Modifier.fillMaxWidth()
                            .heightIn(50.dp, 70.dp)) {
                            Spacer(Modifier.width(8.dp))
                        }
                    }
                }
            }


            if (selectedModule != null) {
                Row {
                    Column(Modifier
                        .then(if(isFullSize) Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                        else Modifier
                            .fillMaxWidth(.7F)
                            .wrapContentHeight()
                            .heightIn(600.dp, 800.dp))
                        .padding(20.dp)
                        .background(Color(0XFFf7f7f7)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top) {

                        Row(Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically) {

                            Text("SELECTED : ${ selectedVideo?.title }", style = MaterialTheme.typography.caption
                                .copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(200),
                                    fontFamily = FontFamily.Serif,
                                    lineHeight = 24.sp
                                ))
                        }

                        Column(
                            Modifier
                                .then(if (isFullSize) Modifier
                                    .fillMaxWidth(.8F)
                                    .fillMaxHeight(.8F)
                                else Modifier
                                    .defaultMinSize(minWidth = 700.dp, minHeight = 300.dp)
                                    .heightIn(300.dp, 400.dp))
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            if(selectedVideo != null) {
                                VideoPlayerCards(
                                    selectedVideo !!.apply {
                                        this.playerState = playerState
                                    }
                                )
                            } else {
                                VideoCard {
                                    Text("Video No Available", style = MaterialTheme.typography
                                        .button
                                        .copy(color = Color.White))
                                }
                            }
                        }

                        Row {
                            ButtonContainer{
                                PlayStopImageButton(playerState) {
                                    playerState = when (playerState) {
                                        VideoState.START-> {
                                            VideoState.PAUSE
                                        }
                                        VideoState.PAUSE -> {
                                            VideoState.START
                                        }
                                        VideoState.INIT -> {
                                            VideoState.START
                                        }
                                        VideoState.REWIND ->  {
                                            VideoState.PAUSE
                                        }
                                        VideoState.FORWARD -> {
                                            VideoState.PAUSE
                                        }
                                        else -> {
                                            VideoState.START
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(50.dp))
                    }

                    Spacer(Modifier.width(10.dp))

                    //Show video list if player is not full size and there are more than one video.
                    LazyColumn(
                        Modifier
                            .padding(top = 20.dp)
                            .wrapContentWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if(!isFullSize && (selectedModule?.videos?.size?:0) > 0) {
                            itemsIndexed(selectedModule!!.videos) { index, video ->
                                Column(
                                    Modifier
                                        .wrapContentWidth()
                                        .wrapContentHeight()
                                        .padding(top = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    CourseCard {
                                        Box(Modifier
                                            // .fillMaxWidth()
                                            .then(if(index == selectedVideoIndex ) Modifier.background(Color.DarkGray)
                                            else Modifier.background(Color.LightGray))
                                            .fillMaxSize()
                                            .clickable {
                                                selectedVideo = video
                                                playerState = VideoState.STOP
                                                selectedVideoIndex = index
                                                isInitial = false
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(1000)
                                                    playerState = VideoState.START
                                                }
                                            }) {
                                            // VideoImageUrl()
                                            ResourceImageDashboard("image/image_java.png") {
                                                selectedVideo = video
                                                playerState = VideoState.STOP
                                                isInitial = false
                                            }
                                            Text(text = "  ${video.title}", style = MaterialTheme.typography.caption
                                                .copy(
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight(200),
                                                    fontFamily = FontFamily.Serif,
                                                    lineHeight = 24.sp
                                                ))
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
            else {
                Row(Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("No Video Available")
                }
            }
        }

    }
}

enum class VideoState { START, PAUSE, STOP, REWIND, RESUME, FORWARD, NONE, INIT}

