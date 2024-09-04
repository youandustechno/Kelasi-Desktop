package ui.quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.DateHelpers
import helpers.startCountDown
import kotlinx.coroutines.*
import models.video.CourseComponent
import models.video.Module
import models.video.Question
import models.video.QuizComponent
import ui.NavHelper
import ui.Route
import ui.utilities.*


@Composable
fun Quiz(navhelper: NavHelper, onClick: (NavHelper) -> Unit) {

    var timeEntry by remember { mutableStateOf("") }
    var course : CourseComponent? by remember { mutableStateOf(null) }
    var module : Module? by remember { mutableStateOf(null) }

    var questionEntry by remember { mutableStateOf("") }

    var shouldAddEntries by remember { mutableStateOf(false) }

    var assertionEntryOne by remember { mutableStateOf("") }
    var assertionEntryTwo by remember { mutableStateOf("") }
    var assertionEntryThree by remember { mutableStateOf("") }
    var assertionEntryFour by remember { mutableStateOf("") }
    var assertionEntryFive by remember { mutableStateOf("") }
    var selectedQuiz: QuizComponent? by remember { mutableStateOf(null) }
    var totalMinutes by remember { mutableStateOf(0) }
    var countDownText: String?  by remember {  mutableStateOf(null) }


    val coroutineScope = rememberCoroutineScope()

    val quizViewModel = QuizViewModel()

    if (navhelper.dataMap.isNotEmpty() && navhelper.dataMap.containsKey("course")) {
       // courseMap["module"] = module
        //courseMap["course"] = course as CourseComponent
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

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        item {

            Column(Modifier
                .widthIn(300.dp, 800.dp)
                .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Column(Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {

                    QuestionFields(questionEntry) {
                        questionEntry = it
                    }

                    Spacer(Modifier.height(10.dp))

                    AssertionFields(assertionEntryOne) {
                        assertionEntryOne = it
                    }

                    AssertionFields(assertionEntryTwo) {
                        assertionEntryTwo = it
                    }

                    AssertionFields(assertionEntryThree) {
                        assertionEntryThree = it
                    }

                    AssertionFields(assertionEntryFour) {
                        assertionEntryFour = it
                    }

                    AssertionFields(assertionEntryFive) {
                        assertionEntryFive = it
                    }

                }

                Spacer(Modifier.height(20.dp))

                Row(Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()) {

                    ConfirmButton("Add to List to be submitted") {
                        //Todo add to a list of question to submit
                         shouldAddEntries = quizViewModel.validateFields(questionEntry,
                            assertionEntryOne,
                            assertionEntryTwo,
                            assertionEntryThree,
                            assertionEntryFour,
                            assertionEntryFive)

                        if(shouldAddEntries) {

                            val assertions = mutableListOf<String>()

                            assertions.add(assertionEntryOne)
                            assertions.add(assertionEntryTwo)
                            assertions.add(assertionEntryThree)
                            assertions.add(assertionEntryFour)
                            assertions.add(assertionEntryFive)

                            quizViewModel.addQuestion(
                                Question(
                                    questionEntry,
                                    assertionEntryOne ,
                                    assertions
                                )
                            )
                            questionEntry = ""
                            assertionEntryOne =""
                            assertionEntryTwo =""
                            assertionEntryThree =""
                            assertionEntryFour =""
                            assertionEntryFive =""
                        }
                    }
                }

                Row(Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()) {
                    ConfirmButton("Save All") {
                        //todo http call to save
                        coroutineScope.launch(Dispatchers.IO) {
                            if(course != null && module?._id != null) {
                               val result=  quizViewModel.addOrUpdateQuiz(
                                    courseComponent = course!!,
                                    moduleId = module?._id!!,
                                    quizId = "",
                                    timeEntry = timeEntry
                                )
                                withContext(Dispatchers.Main) {
                                    print(""+ result)
                                    //onClick.invoke(NavHelper(Route.Dashboard))
                                }
                            }
                        }
                    }

                    DenyButton("Cancel") {
                        //todo clear map and make http call delete that quiz
                        onClick.invoke(NavHelper(Route.Dashboard))
                    }
                }
            }
        }
    }
}