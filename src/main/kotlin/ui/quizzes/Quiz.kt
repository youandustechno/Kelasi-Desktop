package ui.quizzes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.startCountDown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.userquiz.UserQuizComponent
import models.video.CourseComponent
import models.video.Module
import models.video.QuizComponent
import ui.NavHelper
import ui.utilities.AnswerFields
import ui.utilities.DocCards
import ui.utilities.QuestionCards
import ui.utilities.SubmitQuizButton


@Composable
fun Quiz(navHelper: NavHelper, onClick: (NavHelper) -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    var course : CourseComponent? by remember { mutableStateOf(null) }
    var module : Module? by remember { mutableStateOf(null) }

    var selectedQuiz: QuizComponent? by remember { mutableStateOf(null) }
    var totalMinutes by remember { mutableStateOf(0) }
    var countDownText: String?  by remember {  mutableStateOf(null) }
    var selectedOptions by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var isContinueClick : Boolean? by remember { mutableStateOf(null) }

    val quizViewModel = QuizViewModel()

    if (navHelper.dataMap.isNotEmpty() && navHelper.dataMap.containsKey("course")) {
        course = navHelper.dataMap["course"] as CourseComponent
        if(navHelper.dataMap.containsKey("module")) {
            module = navHelper.dataMap["module"] as Module
        }
    }

    if(totalMinutes != 0) {
        LaunchedEffect(totalMinutes) {
            startCountDown(totalMinutes) { remainingTime  ->
                countDownText = remainingTime
            }
        }
    }

    Column (Modifier.fillMaxSize()){
        Column(Modifier.fillMaxWidth()
            .wrapContentHeight()
        ) {
            if(module != null) {
                LazyRow {
                    itemsIndexed(module!!.quiz) { index, interro ->

                        DocCards({
                            totalMinutes = 0
                            countDownText = ""
                            selectedQuiz = interro
                        }) {
                            if(interro._id != null) {
                                Spacer(Modifier.height(5.dp))
                                Text("Quiz Number ${index + 1}",
                                    style = MaterialTheme.typography.caption.copy(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(200),
                                        fontFamily = FontFamily.Serif,
                                        lineHeight = 24.sp
                                    ))
                                Spacer(Modifier.height(2.dp))
                                Text(text = interro.title, style = MaterialTheme.typography.caption
                                    .copy(fontSize = 12.sp, lineHeight = 15.sp))
                            }
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            if(selectedQuiz != null && selectedQuiz!!.time > 0 && totalMinutes == 0) {
                totalMinutes = selectedQuiz!!.time
            }

            Box(Modifier.fillMaxWidth()){
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
        }

        //if(selectedQuiz != null && selectedQuiz!!.problems?.isNotEmpty() == true) {
        if(selectedQuiz != null) {
            Spacer(Modifier.height(10.dp))
            //Questions
            if(isContinueClick == null) {
                Column(Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Spacer(Modifier.height(10.dp))
                    Column(Modifier.width(450.dp)
                        .height(300.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        Text("Before you start this quiz. Make sure you have stable internet connection " +
                                "and once you click on continue, you can not go off this screen or close it before you submit it." +
                                "Failing to do so, you will get the lowest mark for this quiz",
                            style = MaterialTheme.typography.caption.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(200),
                                fontFamily = FontFamily.Serif,
                                lineHeight = 24.sp
                            ))

                        Spacer(Modifier.height(20.dp))
                        Row(Modifier
                            .sizeIn(150.dp, 70.dp, 300.dp, 90.dp)
                            .padding(20.dp)) {

                            SubmitQuizButton("START QUIZ") {
                                coroutineScope.launch(Dispatchers.IO) {
                                    val response = quizViewModel.submitQuiz(
                                        UserQuizComponent(
                                            firstName = "",
                                            lastName = "",
                                            grade = 0,
                                            total = 10,
                                            moduleName = "",
                                            topicName = "",
                                            topicId = "",
                                            quizId = selectedQuiz!!._id!!,
                                            hasResponseField = false
                                        )
                                    )
                                    withContext(Dispatchers.Main) {
                                        if(response?.score != null) {
                                            isContinueClick = true
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else  if(isContinueClick == true){
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {

                    items(selectedQuiz!!.problems!!) { problem ->
                        var answerEntry by remember { mutableStateOf("") }

                        Column(Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(start = 40.dp, end = 40.dp, top = 10.dp)) {
                            QuestionCards {
                                Text("${problem.positon}. ${problem.question}", style = MaterialTheme.typography.body2)
                                Spacer(Modifier.height(10.dp))
                                problem.assertions?.forEachIndexed { index, option ->
                                    Row(Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically) {
                                        RadioButton(
                                            selected = selectedOptions[problem.question] == index,
                                            onClick = {
                                                selectedOptions = selectedOptions.toMutableMap().apply {
                                                    put(problem.question, index)
                                                }
                                            }
                                        )
                                        Spacer(Modifier.width(5.dp))
                                        Text(option,  style = MaterialTheme.typography.body2)
                                    }
                                }?: kotlin.run {
                                    AnswerFields(answerEntry) {
                                        answerEntry = it
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
                                coroutineScope.launch(Dispatchers.IO) {
                                    val response = quizViewModel.submitQuiz(
                                        UserQuizComponent(
                                            firstName = "",
                                            lastName = "",
                                            grade = 0,
                                            total = 10,
                                            moduleName = "",
                                            topicName = "",
                                            topicId = "",
                                            quizId = selectedQuiz!!._id!!,
                                            hasResponseField = false
                                        )
                                    )
                                    withContext(Dispatchers.Main) {
                                        if(response?.score != null) {
                                            //display score
                                            isContinueClick = false
                                        } else {

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Column(Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Spacer(Modifier.height(10.dp))
                    Column(Modifier.width(450.dp)
                        .height(300.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        Text("Your score is * some score * over *some total*",
                            style = MaterialTheme.typography.caption.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(200),
                                fontFamily = FontFamily.Serif,
                                lineHeight = 24.sp
                            ))

                        Spacer(Modifier.height(20.dp))
                        Row(Modifier
                            .sizeIn(150.dp, 70.dp, 300.dp, 90.dp)
                            .padding(20.dp)) {
                            SubmitQuizButton("CLOSE") {

                            }
                        }
                    }
                }
            }
        }
    }
}