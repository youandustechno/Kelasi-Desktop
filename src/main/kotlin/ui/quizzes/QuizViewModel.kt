package ui.quizzes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.CourseResponse
import models.UserQuizResponse
import models.userquiz.UserQuizApi
import models.userquiz.UserScoreData
import models.video.*
import ui.dashboards.ViewModel
import ui.utilities.ScoreWrapper

class QuizViewModel: ViewModel() {

    suspend fun submitQuiz(userQuiz: UserScoreData) : UserQuizResponse? {
       return UserQuizApi().submitUserQuiz(userQuiz)
    }

    fun validateAnswer(
        responsesList: MutableMap<String, String>,
        questions: List<Question>?): ScoreWrapper {
        var count = 0
        var total = 0
        var max = 0

        questions?.forEach {
            if(responsesList[it.question] == it.answer && it.assertions?.size != 0) {
                count ++
            }

            if(it.assertions?.size != 0) {
                total ++
            }
            max++
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