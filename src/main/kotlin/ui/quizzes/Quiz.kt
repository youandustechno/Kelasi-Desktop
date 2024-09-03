package ui.quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.video.CourseComponent
import models.video.Module
import models.video.Question
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

    Box(Modifier.fillMaxWidth()
        .wrapContentHeight()
    ) {

        Column(modifier = Modifier
            .wrapContentHeight()
            .width(300.dp)
            .align(Alignment.CenterEnd),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center){

            TimeFields(timeEntry) {
               timeEntry = it
            }
            Spacer(Modifier.height(5.dp))
            Text("Eg. 60 for 1 hours and 120 for 2 hours" ,
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