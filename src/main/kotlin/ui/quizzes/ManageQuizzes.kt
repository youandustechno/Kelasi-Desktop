package ui.quizzes


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import models.video.CourseComponent
import models.video.Module
import ui.NavHelper
import ui.utilities.*

@Composable
fun ManageQuizzes(navhelper: NavHelper, onClick: (NavHelper) -> Unit) {

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

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        if(course?.modules != null) {
            items(course?.modules!!) { module ->

                LazyVerticalGrid(
                    modifier = Modifier
                        .widthIn(1000.dp, 1500.dp)
                        .heightIn(500.dp, 700.dp)
                        .padding(start = 50.dp, end = 20.dp)
                    ,
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(module.quiz) { quiz ->
                        Column(
                            Modifier.wrapContentHeight()
                                .wrapContentWidth()
                                .padding(10.dp)
                                .clickable {  },
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
                                            Text(text = ""+ quiz._id?.substring(quiz._id?.length!! - 5), style = MaterialTheme.typography.caption
                                                .copy(
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight(200),
                                                    fontFamily = FontFamily.Serif,
                                                    lineHeight = 24.sp
                                                )
                                            )
                                            Spacer(Modifier.height(2.dp))
                                            Text(text = ""+quiz.modulename, style = MaterialTheme.typography.caption
                                                .copy(fontSize = 12.sp, lineHeight = 15.sp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        else {
            item {
                ErrorCard {
                    Text("There is no quiz added yet 1",
                        style = MaterialTheme.typography
                            .button
                            .copy(color = Color.DarkGray))
                }
            }
        }
    }
}