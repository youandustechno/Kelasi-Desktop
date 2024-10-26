package ui.dashboards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.CoursesListResponse
import models.auth.UserDataModel
import models.video.CourseComponent
import ui.Cache
import ui.NavHelper
import ui.NavKeys.COURSE
import ui.NavKeys.USER_KEY
import ui.Route
import ui.utilities.*

@Composable
fun Dashboard(navigationState: NavHelper,
              courses: (CoursesListResponse?)-> Unit,
              onClick: (NavHelper) -> Unit) {

    var coursesList: CoursesListResponse? by remember { mutableStateOf(CoursesListResponse())}
    var courses: List<CourseComponent>? by remember { mutableStateOf(null)}
    var isCoursesAvailable: Boolean? by remember { mutableStateOf(null) }
    var user : UserDataModel? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()

    //courseMap["user"] = userSuccess.user
    //NavHelper(Route.Dashboard, courseMap)

    if(navigationState.dataMap.containsKey(USER_KEY)) {
        user = navigationState.dataMap[USER_KEY] as UserDataModel
        user?.let {
            Cache.updateUser(it)
        }
    }

    LaunchedEffect(Unit) {
        if(isCoursesAvailable != true) {
            if(Cache.courseCache == null ) {
                coursesList =  DashboardViewModel().getCoursesList()
            }
            courses(coursesList)
            isCoursesAvailable = true
            courses = coursesList?.courses?.filter { it.level?.contains( Cache.userCache?.level) == true }
            Cache.courseCache = courses
            isCoursesAvailable = courses?.isNotEmpty() == true || courses != null
        }
    }

    LazyColumn(Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Row(Modifier.fillMaxWidth()
                .wrapContentHeight(),
                horizontalArrangement = Arrangement.End) {
                ResourceImageDashboard("image/black_people.webp")  {
                     coroutineScope.launch(Dispatchers.IO) {
                        val classes = DashboardViewModel().getCoursesList()
                         withContext(Dispatchers.Main) {
                             coursesList = classes
                             courses(classes )
                         }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
        }

        item {

            if(courses != null) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .widthIn(1000.dp, 1500.dp)
                        .heightIn(800.dp, 1200.dp)
                        .padding(start = 50.dp, end = 20.dp)
                    ,
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(courses!!) { course ->
                        Column(
                            Modifier.wrapContentHeight()
                                .wrapContentWidth()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {

                            AddCoursesCards {
                                Box(Modifier
                                    .clickable {
                                        val map = mutableMapOf<String, Any>()
                                        map[COURSE] = course
                                        onClick.invoke(NavHelper(Route.VideosList, map))
                                    }.fillMaxSize()) {
                                    Row(Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start ) {
                                        Column(Modifier
                                            .padding(start = 5.dp, end = 5.dp, top = 3.dp, bottom = 1.dp)) {
                                            ResourceImageDashboard("image/black_people.webp")
                                            Text(text = ""+course.name, style = MaterialTheme.typography.caption
                                                .copy(
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight(200),
                                                    fontFamily = FontFamily.Serif,
                                                    lineHeight = 24.sp
                                                )
                                            )
                                            Spacer(Modifier.height(2.dp))
                                            Text(text = ""+course.description, style = MaterialTheme.typography.caption
                                                .copy(fontSize = 12.sp, lineHeight = 15.sp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                if(isCoursesAvailable == null){
                    Column {
                        Text("Loading")
                    }
                }  else if(isCoursesAvailable == true && coursesList?.errorComponent != null) {
                    ErrorCard {
                        Text("Sorry for the inconvenience, we are unable to find the resource requested",
                            style = MaterialTheme.typography
                                .button
                                .copy(color = Color.DarkGray))
                    }
                }
            }

        }
    }
}