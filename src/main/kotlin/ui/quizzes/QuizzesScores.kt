package ui.quizzes


import androidx.compose.runtime.*
import models.video.CourseComponent
import models.video.Module
import ui.NavHelper

@Composable
fun QuizzesScores(navhelper: NavHelper, onClick: (NavHelper) -> Unit) {

    var course : CourseComponent? by remember { mutableStateOf(null) }
    var module : Module? by remember { mutableStateOf(null) }
    var position by mutableStateOf(0)


    //val coroutineScope = rememberCoroutineScope()

    if (navhelper.dataMap.isNotEmpty() && navhelper.dataMap.containsKey("course")) {
        // courseMap["module"] = module
        //courseMap["course"] = course as CourseComponent
        course = navhelper.dataMap["course"] as CourseComponent
        if(navhelper.dataMap.containsKey("module")) {
            module = navhelper.dataMap["module"] as Module
        }
    }
}