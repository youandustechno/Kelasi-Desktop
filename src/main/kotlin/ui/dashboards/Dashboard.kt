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
import models.CoursesListResponse
import ui.NavHelper
import ui.Route
import ui.utilities.*

@Composable
fun Dashboard(courses: (CoursesListResponse?)-> Unit,
              onClick: (NavHelper) -> Unit) {

    var coursesList: CoursesListResponse? by remember { mutableStateOf(CoursesListResponse())}
    var isCoursesAvailable: Boolean? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        coursesList = DashboardViewModel().getCoursesList()
        courses(coursesList)
        isCoursesAvailable = true
    }

    LazyColumn(Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

//        item {
//            Row( Modifier
//                .fillMaxWidth()
//                .padding(20.dp),
//                horizontalArrangement = Arrangement.End,
//                verticalAlignment = Alignment.Top) {
//                //todo  replace with plus icon
//                ResourceImage50by50("image/upload.png") {
//                    onClick(NavHelper(Route.ManageVideo))
//                }
//            }
//        }

        item {

            if(coursesList?.courses != null) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .widthIn(1000.dp, 1500.dp)
                        .heightIn(800.dp, 1200.dp)
                        .padding(start = 50.dp, end = 20.dp)
                    ,
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(coursesList?.courses!!) { course ->
                        Column(
                            Modifier.wrapContentHeight()
                                .wrapContentWidth()
                                .clickable {
                                    val map = mutableMapOf<String, Any>()
                                    map["course"] = course
                                    onClick.invoke(NavHelper(Route.VideosList, map))
                                }
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {

                            AddCoursesCards {
                                Box(Modifier.fillMaxSize()) {
                                    Row(Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start ) {
                                        //CouseImageUrl("https://picsum.photos/id/237/100/200")
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

//                                    Row(Modifier
//                                        .padding(start = 5.dp, end = 5.dp, top = 3.dp, bottom = 1.dp)
//                                        .align(Alignment.BottomCenter)) {
//                                        DashboardButton("SELECT") {
//                                            val map = mutableMapOf<String, Any>()
//                                            map["course"] = course
//                                            onClick.invoke(NavHelper(Route.VideosList, map))
//                                        }
//                                    }
                                }
                            }
                        }
                    }
                }
            } else {
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