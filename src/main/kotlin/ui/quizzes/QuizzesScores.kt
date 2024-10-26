package ui.quizzes


import androidx.compose.runtime.*
import models.video.CourseComponent
import models.video.Module
import ui.NavHelper
import ui.NavKeys.COURSE
import ui.NavKeys.MODULE

@Composable
fun QuizzesScores(navhelper: NavHelper, onClick: (NavHelper) -> Unit) {

    var course : CourseComponent? by remember { mutableStateOf(null) }
    var module : Module? by remember { mutableStateOf(null) }
    var position by mutableStateOf(0)


    //val coroutineScope = rememberCoroutineScope()

    if (navhelper.dataMap.isNotEmpty() && navhelper.dataMap.containsKey(COURSE)) {
        // courseMap["module"] = module
        //courseMap["course"] = course as CourseComponent
        course = navhelper.dataMap[COURSE] as CourseComponent
        if(navhelper.dataMap.containsKey(MODULE)) {
            module = navhelper.dataMap[MODULE] as Module
        }
    }
}