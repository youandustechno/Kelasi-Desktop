package ui.quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.startCountDown
import models.video.CourseComponent
import models.video.Module
import models.video.QuizComponent
import ui.NavHelper
import ui.utilities.QuestionCards
import ui.utilities.SubmitQuizButton


@Composable
fun Quiz(navhelper: NavHelper, onClick: (NavHelper) -> Unit) {

    var course : CourseComponent? by remember { mutableStateOf(null) }
    var module : Module? by remember { mutableStateOf(null) }

    var selectedQuiz: QuizComponent? by remember { mutableStateOf(null) }
    var totalMinutes by remember { mutableStateOf(0) }
    var countDownText: String?  by remember {  mutableStateOf(null) }
    var selectedOptions by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    val quizViewModel = QuizViewModel()

    if (navhelper.dataMap.isNotEmpty() && navhelper.dataMap.containsKey("course")) {
        course = navhelper.dataMap["course"] as CourseComponent
        if(navhelper.dataMap.containsKey("module")) {
            module = navhelper.dataMap["module"] as Module
        }
    }

    if(totalMinutes != 0) {
        LaunchedEffect(totalMinutes) {
            startCountDown(totalMinutes) { remainingTime  ->
                countDownText = remainingTime
            }
        }
    }

    Box(Modifier.fillMaxWidth()
        .wrapContentHeight()
    ) {
        if(module != null) {
            LazyRow {
                items(module!!.quiz) { interro ->
                    Row(Modifier.wrapContentWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp)) {
                        Row(
                            Modifier
                                .sizeIn(minWidth = 60.dp, minHeight = 60.dp, maxWidth = 100.dp, maxHeight = 100.dp)
                                .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                                .clickable {
                                    totalMinutes = 0
                                    countDownText = ""
                                    selectedQuiz = interro
                                }
                                .padding(start = 10.dp, top= 5.dp, bottom = 5.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(interro._id!!.substring(interro._id!!.length -1, interro._id!!.length -1),
                                style = MaterialTheme.typography.caption.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(200),
                                    fontFamily = FontFamily.Serif,
                                    lineHeight = 24.sp
                                ))
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }

        if(selectedQuiz != null && selectedQuiz!!.time > 0 && totalMinutes == 0) {
            totalMinutes = selectedQuiz!!.time
        }

        Column(modifier = Modifier
                .wrapContentHeight()
                .width(300.dp)
                .align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center){

                Text(countDownText?:"" ,
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.caption)
        }

    }

    if(selectedQuiz != null && selectedQuiz!!.problems?.isNotEmpty() == true) {
        Spacer(Modifier.height(10.dp))
        //Questions
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            items(selectedQuiz!!.problems!!) { problem ->

                Column(Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 40.dp, end = 40.dp, top = 10.dp)) {
                    QuestionCards {
                        Text("${problem.positon}. ${problem.question}", style = MaterialTheme.typography.body2)
                        Spacer(Modifier.height(10.dp))
                        problem.assertions?.forEachIndexed { index, option ->
                            Row {
                                RadioButton(
                                    selected = selectedOptions[problem.positon] == index,
                                    onClick = {
                                        selectedOptions = selectedOptions.toMutableMap().apply {
                                            put(problem.positon, index)
                                        }
                                    }
                                )
                                Spacer(Modifier.width(5.dp))
                                Text(option)
                            }
                        }
                    }
                }
            }

            item {
                //Submit quiz
                Spacer(Modifier.height(30.dp))
                Row(Modifier
                    .sizeIn(150.dp, 70.dp, 300.dp, 90.dp)
                    .padding(20.dp)) {

                    SubmitQuizButton("SUBMIT QUIZ") {
                        //quizViewModel.addOrUpdateQuiz()
                    }
                }
            }
        }
    }
}