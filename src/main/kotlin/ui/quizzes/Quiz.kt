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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.startCountDown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.QuizScore
import models.userquiz.Answer
import models.userquiz.ScoreInfo
import models.userquiz.UserScoreData
import models.userquiz.UserScoreRequest
import models.video.CourseComponent
import models.video.Module
import models.video.QuizComponent 
import ui.Cache.userCache
import ui.LocalizedStrings
import ui.LocalizedStrings.ALREADY_TAKEN
import ui.LocalizedStrings.BEFORE_YOU_TAKE
import ui.LocalizedStrings.CLOSE
import ui.LocalizedStrings.CORRECT_ANSWER
import ui.LocalizedStrings.NO_GRADED_YET
import ui.LocalizedStrings.QUESTION
import ui.LocalizedStrings.QUIZ_NUMBER
import ui.LocalizedStrings.START_QUIZ
import ui.LocalizedStrings.SUBMIT_QUIZ
import ui.LocalizedStrings.WELCOME_TO_ASSESSMENT
import ui.LocalizedStrings.YOUR_ANSWER
import ui.NavHelper
import ui.NavKeys.COURSE
import ui.NavKeys.EMPTY
import ui.NavKeys.MODULE
import ui.utilities.*
import ui.utilities.Others.formatNumber


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
    var isSubmitted by remember { mutableStateOf(false) }
    var quizState : QuizState by remember { mutableStateOf(QuizState.NONE) }

    val quizScores: MutableList<QuizScore>? by remember { mutableStateOf(mutableListOf()) }

    var score by remember { mutableStateOf(ScoreWrapper()) }

    var quizFinalScore: QuizFinalScore? by remember { mutableStateOf(null) }

    var answers: MutableList<Answer> by remember { mutableStateOf(mutableListOf()) }

    val quizViewModel = QuizViewModel()

    if (navHelper.dataMap.isNotEmpty() && navHelper.dataMap.containsKey(COURSE) && quizState == QuizState.NONE) {
        course = navHelper.dataMap[COURSE] as CourseComponent
        if(navHelper.dataMap.containsKey(MODULE)) {
            LaunchedEffect(Unit) {
                val courses = quizViewModel.getCourseScores(
                    UserScoreRequest(
                         userRef = userCache?._id.toString(),
                         topicId = course?._id.toString(),
                        topicName = course?.name.toString()
                    )
                )

                quizScores?.clear()
                courses?.cours?.scores?.let {
                    quizScores?.addAll(it)
                }
                quizState = QuizState.INITIAL
            }
            module = navHelper.dataMap[MODULE] as Module
        }
    }

    if(totalMinutes != 0 && quizState == QuizState.START) {
        LaunchedEffect(totalMinutes) {
            startCountDown(totalMinutes) { remainingTime  ->
                countDownText = remainingTime
            }
        }
    }

    Column (Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()
            .wrapContentHeight()
        ) {
            if(module != null && (quizState == QuizState.INITIAL || quizState == QuizState.SUBMIT || quizState == QuizState.DISPLAY)) {
                //Displays Quizzes list horizontally
                LazyRow {
                    itemsIndexed(module!!.quiz) { index, interro ->
                        val found = quizScores?.find { it.quizRef == interro._id }
                        DocCards({
                            if(!found?.score.isNullOrEmpty()) {
                                totalMinutes = 0
                                countDownText = EMPTY
                                quizState = QuizState.DISPLAY
                                quizFinalScore = QuizFinalScore((index + 1).toString(), found)
                                selectedQuiz = null

                            } else {
                                totalMinutes = 0
                                countDownText = EMPTY
                                quizState = QuizState.CONTINUE
                                selectedQuiz = interro
                            }

                        }, selectedQuiz != null || quizState == QuizState.DISPLAY ) {

                            if(interro._id != null) {
                                Spacer(Modifier.height(5.dp))
                                Text("${LocalizedStrings.get(QUIZ_NUMBER)} ${index + 1}",
                                    style = MaterialTheme.typography.caption.copy(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(200),
                                        fontFamily = FontFamily.Serif,
                                        lineHeight = 24.sp
                                    ))
                                Spacer(Modifier.height(2.dp))
                                Text(text = interro.title, style = MaterialTheme.typography.caption
                                    .copy(fontSize = 12.sp, lineHeight = 15.sp))

                                if(!found?.score.isNullOrEmpty()) {
                                    Text(text = LocalizedStrings.get(ALREADY_TAKEN),
                                        style = MaterialTheme.typography.caption
                                        .copy(fontSize = 12.sp, lineHeight = 15.sp))
                                    Spacer(Modifier.height(2.dp))
                                }

                            }
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            if(selectedQuiz != null && selectedQuiz!!.time >= 0
                && totalMinutes == 0 && quizState == QuizState.START) {
                totalMinutes = selectedQuiz!!.time
                selectedQuiz!!.problems?.forEach {
                    answers.add(Answer(it.question, EMPTY, rightAnswer = it.answer))
                }
            }

            //Display timer
            if(quizState == QuizState.START) {
                Box(Modifier.fillMaxWidth()){
                    Column(modifier = Modifier
                        .wrapContentHeight()
                        .width(300.dp)
                        .align(Alignment.CenterEnd),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center){

                        Text(countDownText?:EMPTY ,
                            modifier = Modifier.padding(start = 10.dp),
                            style = MaterialTheme.typography.caption.copy(fontSize = 14.sp))
                    }
                }
            }
        }

        //if(selectedQuiz != null && selectedQuiz!!.problems?.isNotEmpty() == true) {
        if(selectedQuiz != null && userCache?.level != "Parent") {
            Spacer(Modifier.height(10.dp))
            //Questions
            if(quizState == QuizState.CONTINUE) {
                Column(Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Spacer(Modifier.height(10.dp))
                    Column(Modifier.width(450.dp)
                        .heightIn(300.dp, 400.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        Row (Modifier.fillMaxWidth()
                            .padding(end = 20.dp, bottom = 50.dp),
                            horizontalArrangement = Arrangement.End) {
                            ResourceImage30by30("image/icon_close.svg")  {
                                quizState = QuizState.INITIAL
                            }
                        }

                        Text(LocalizedStrings.get(BEFORE_YOU_TAKE),
                            style = MaterialTheme.typography.caption.copy(
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Light,
                                fontFamily = FontFamily.Serif,
                                lineHeight = 24.sp
                            ))

                        Spacer(Modifier.height(20.dp))
                        Row(Modifier
                            .sizeIn(150.dp, 70.dp, 300.dp, 100.dp)
                            .padding(20.dp)) {

                            SubmitQuizButton(LocalizedStrings.get(START_QUIZ).ifEmpty { "START" }) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    score = quizViewModel.validateAnswer(answers)
                                    val response = userCache?.let { user ->
                                        quizViewModel.submitQuiz(
                                            UserScoreData(
                                                userRef = user._id,
                                                firstName = user.firstName,
                                                lastName = user.lastName,
                                                middleName = user.middleName,
                                                level = user.level,
                                                scoreInfo = ScoreInfo(
                                                    quizRef = selectedQuiz?._id!!,
                                                    score = formatNumber((score.temp) * 10).toString(),
                                                    total = (score.total.toInt() * 10).toString(),
                                                    module = module?._id ?: module?.name!!,
                                                    topicRef = course?._id?:course?.name!!,
                                                    topicName =  course?.name ?: EMPTY,
                                                    created = Others.getCurrentDate(),
                                                    responses = answers,
                                                    pending = true,
                                                    hasResponseField = false // Todo change this
                                                )
                                            )
                                        )
                                    }
                                    delay(500L)
                                    withContext(Dispatchers.Main) {
                                        if(response?.userScores != null) {
                                            isContinueClick = true
                                            quizState = QuizState.START
                                        }
                                        else {
                                            isContinueClick = null
                                            quizState = QuizState.START
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            else if(quizState == QuizState.START) {

                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {

                    itemsIndexed(selectedQuiz!!.problems!!) { outerIndex, problem ->
                        var answerEntry by remember { mutableStateOf(EMPTY) }

                        Column(Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(start = 40.dp, end = 40.dp, top = 10.dp)) {
                            QuestionCards {
                                Text("${outerIndex + 1}. ${problem.question}", style = MaterialTheme.typography.body2)
                                Spacer(Modifier.height(10.dp))
                                if(!problem.assertions.isNullOrEmpty()) {
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

                                                    answers.find { it.question == problem.question }?.let {
                                                        it.answer = option
                                                        it.isAssertion = true
                                                    }
//                                                    answers[outerIndex - 1] = Answer(
//                                                        question = problem.question,
//                                                        answer = option,
//                                                        rightAnswer = problem.answer,
//                                                        isAssertion = true
//                                                    )
                                                    //questions[problem.question] = option
                                                }
                                            )
                                            Spacer(Modifier.width(5.dp))
                                            Text(option,  style = MaterialTheme.typography.body2)
                                        }
                                    }

                                }
                                else {
                                    AnswerField(answerEntry) { entry ->

                                        answers.find { it.question == problem.question }?.let {
                                            it.answer = entry
                                        }
                                        answerEntry = entry
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

                            SubmitQuizButton(LocalizedStrings.get(SUBMIT_QUIZ)) {
                                score = quizViewModel.validateAnswer(answers)
                                coroutineScope.launch(Dispatchers.IO) {
                                    val response = userCache?.let { user ->

                                        quizViewModel.submitQuiz(
                                            UserScoreData(
                                                userRef = user._id,
                                                firstName = user.firstName,
                                                lastName = user.lastName,
                                                middleName = user.middleName,
                                                level = user.level,
                                                scoreInfo = ScoreInfo(
                                                    quizRef = selectedQuiz?._id!!,
                                                    score = formatNumber((score.temp) * 10).toString(),
                                                    total = (score.total.toInt() * 10).toString(),
                                                    module = module?._id ?: module?.name!!,
                                                    topicRef = course?._id?:course?.name!!,
                                                    topicName =  course?.name ?: "",
                                                    created = Others.getCurrentDate(),
                                                    responses = answers,
                                                    pending = true,
                                                    hasResponseField = false // Todo change this
                                                )
                                            )
                                        )
                                    }
                                    delay(500L)
                                    withContext(Dispatchers.Main) {
                                        if(response?.userScores != null) {
                                            //display score
                                            isSubmitted = true
                                            quizState = QuizState.SUBMIT
                                        } else {
                                            quizState = QuizState.SUBMIT
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            else if(quizState == QuizState.SUBMIT) {
                if(isSubmitted) {
                    //refresh to get scores updates
                    coroutineScope.launch (Dispatchers.IO){
                        val courseResponse = course?._id?.let { quizViewModel.getCourse(it) }
                        withContext(Dispatchers.Main) {
                            if(courseResponse?.courses != null) {
                                course = courseResponse.courses
                                module = module?._id?.let {id -> course?.modules?.find { it._id?.equals(id) == true } }
                            }
                            isSubmitted = false
                        }
                    }
                }
                Column(Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Spacer(Modifier.height(10.dp))
                    LazyColumn(Modifier.width(450.dp)
                        .wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        item {
                            val scoreText = if(score.tempTotal == score.total) {
                                "You scored  ${ formatNumber((score.temp) * 10) } out of ${score.tempTotal.toInt() * 10}"
                            } else{
                                "You have  ${formatNumber((score.temp))  } correct answers. \n" +
                                        "The instructor has to grade the no graded part question to see the final score."
                            }
                            Text(scoreText,
                                style = MaterialTheme.typography.caption.copy(
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Light,
                                    fontFamily = FontFamily.Serif,
                                    lineHeight = 24.sp
                                ))
                            Spacer(Modifier.height(10.dp))
                        }

                        itemsIndexed(selectedQuiz!!.problems!!) {index, item ->
                            Column(Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                QuestionCards {
                                    Text("${LocalizedStrings.get(QUESTION)}: ${item.question}",
                                        style = MaterialTheme.typography.caption.copy(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight(200),
                                            fontFamily = FontFamily.Serif,
                                            lineHeight = 24.sp
                                        ))
                                    Spacer(Modifier.height(10.dp))
                                    val currentAnswer = answers.find { it.question == item.question }
                                    Text("${LocalizedStrings.get(YOUR_ANSWER)}:  ${ currentAnswer?.answer}",
                                        style = MaterialTheme.typography.caption.copy(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight(200),
                                            fontFamily = FontFamily.Serif,
                                            lineHeight = 24.sp
                                        ))
                                    Spacer(Modifier.height(10.dp))
                                    Row(Modifier.fillMaxWidth().padding(end = 5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("${LocalizedStrings.get(YOUR_ANSWER)}:  ${item.answer}",
                                            style = MaterialTheme.typography.caption.copy(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight(200),
                                                fontFamily = FontFamily.Serif,
                                                lineHeight = 24.sp
                                            ))
                                        if(item.answer.equals(currentAnswer?.answer, true)
                                            && item.assertions?.isEmpty() != true ) {
                                            ResourceImage30by30("image/icon_check.svg")
                                        }
                                        else if( item.assertions?.isNullOrEmpty() == true) {
                                            Text("No graded yet",
                                                color = Color(0XFF338dff),
                                                style = MaterialTheme.typography.caption.copy(
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight(200),
                                                    fontFamily = FontFamily.Serif,
                                                    lineHeight = 24.sp
                                                ))
                                        }
                                        else {
                                            ResourceImage30by30("image/icon_clear.svg")
                                        }
                                    }
                                }
                            }
                            Spacer(Modifier.height(20.dp))
                        }

                        item {
                            Spacer(Modifier.height(20.dp))
                            Row(Modifier
                                .sizeIn(150.dp, 70.dp, 300.dp, 90.dp)
                                .padding(20.dp)) {
                                SubmitQuizButton(LocalizedStrings.get(CLOSE)) {
                                    quizState = QuizState.INITIAL
                                }
                            }
                        }
                    }
                }
            }
        }
        else if(quizState == QuizState.DISPLAY) {

            Column(Modifier.fillMaxWidth()
                .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Spacer(Modifier.height(20.dp))
                LazyColumn(Modifier.widthIn(450.dp, 650.dp)
                    .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    item {
                        Column {
                            Text("${LocalizedStrings.get(QUIZ_NUMBER)} ${quizFinalScore?.index}",
                                style = MaterialTheme.typography.caption.copy(
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight(200),
                                    fontFamily = FontFamily.Serif,
                                    lineHeight = 24.sp
                                ))
                            Spacer(Modifier.height(20.dp))
                            Text("You scored ${quizFinalScore?.quiz?.score} out of  ${quizFinalScore?.quiz?.total}",
                                style = MaterialTheme.typography.caption.copy(
                                    fontSize = 18.sp,
                                    color = Color.Blue,
                                    fontWeight = FontWeight(200),
                                    fontFamily = FontFamily.Serif,
                                    lineHeight = 20.sp
                                ))
                            Spacer(Modifier.height(20.dp))
                        }
                    }

                    if(!quizFinalScore?.quiz?.responses.isNullOrEmpty()) {

                        items(quizFinalScore?.quiz?.responses!!) { item ->

                            Column(Modifier
                                .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                QuestionCards {
                                    Text("${LocalizedStrings.get(QUESTION)}: ${item.question}",
                                        style = MaterialTheme.typography.caption.copy(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight(200),
                                            fontFamily = FontFamily.Serif,
                                            lineHeight = 24.sp
                                        ))
                                    Spacer(Modifier.height(10.dp))
//                                    var ans = answers.find { it.question == item.question }
//                                    val currentAnswer: Answer by remember { mutableStateOf(ans) }
                                    Text("${LocalizedStrings.get(YOUR_ANSWER)}:  ${ item.answer}",
                                        style = MaterialTheme.typography.caption.copy(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight(200),
                                            fontFamily = FontFamily.Serif,
                                            lineHeight = 24.sp
                                        ))
                                    Spacer(Modifier.height(10.dp))
                                    Row(Modifier.fillMaxWidth().padding(0.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("${LocalizedStrings.get(CORRECT_ANSWER)}:  ${item.rightAnswer}",
                                            style = MaterialTheme.typography.caption.copy(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight(200),
                                                fontFamily = FontFamily.Serif,
                                                lineHeight = 24.sp
                                            ))

                                        if(!item.isAssertion && quizFinalScore?.quiz?.pending.toBoolean()) {
                                            Text(LocalizedStrings.get(NO_GRADED_YET),
                                                color = Color(0XFF338dff),
                                                style = MaterialTheme.typography.caption.copy(
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight(200),
                                                    fontFamily = FontFamily.Serif,
                                                    lineHeight = 24.sp
                                                ))
                                        }
                                        else if(!item.isAssertion && !quizFinalScore?.quiz?.pending.toBoolean()) {

                                            if(item.isValidAnswer) {
                                                ResourceImage30by30("image/icon_check.svg")
                                            } else {
                                                ResourceImage30by30("image/icon_clear.svg")
                                            }

                                        } else  if(item.answer.equals(item.rightAnswer, true)) {
                                            ResourceImage30by30("image/icon_check.svg")
                                        }
                                        else {
                                            ResourceImage30by30("image/icon_clear.svg")
                                        }
                                    }
                                }
                            }
                            Spacer(Modifier.height(20.dp))

                        }
                    }
                }
            }
        }
        else {
            val welcomeText = LocalizedStrings.get(WELCOME_TO_ASSESSMENT)

            Column(Modifier.fillMaxWidth()
                .heightIn(300.dp, 450.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Text(welcomeText,
                    style = MaterialTheme.typography.caption.copy(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 24.sp
                    ))
            }
        }
    }
}

enum class QuizState { INITIAL, SUBMIT, CONTINUE, DISPLAY, START, NONE}

data class QuizFinalScore(var index: String, var  quiz: QuizScore?)