package ui.quizzes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.CourseResponse
import models.QuizzesResponse
import models.UserQuizResponse
import models.userquiz.Answer
import models.userquiz.UserQuizApi
import models.userquiz.UserScoreData
import models.userquiz.UserScoreRequest
import models.video.*
import ui.dashboards.ViewModel
import ui.utilities.ScoreWrapper

class QuizViewModel: ViewModel() {

    suspend fun submitQuiz(userQuiz: UserScoreData) : UserQuizResponse? {
       return UserQuizApi().submitUserQuiz(userQuiz)
    }

    suspend fun getCourseScores(userQuiz: UserScoreRequest) : QuizzesResponse? {
        return UserQuizApi().getUserScores(userQuiz)
    }

    fun validateAnswer(responsesList: MutableList<Answer>): ScoreWrapper {
        var count = 0
        var total = 0
        var max = 0

        responsesList.forEach {
            if(it.answer == it.rightAnswer || it.isValidAnswer) {
                count ++
            }

            if(it.isAssertion) {
                total++
            }
            max ++
        }

        return ScoreWrapper(temp = count.toDouble(),
            tempTotal = total.toDouble(),
            total = max.toDouble())
    }

    var course by mutableStateOf<CourseResponse?>(null)
        private set

    var questions by mutableStateOf(emptyList<Question>().toMutableList())
        private set
}