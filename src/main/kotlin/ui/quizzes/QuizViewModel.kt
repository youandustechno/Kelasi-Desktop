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

class QuizViewModel: ViewModel() {

    suspend fun submitQuiz(userQuiz: UserScoreData) : UserQuizResponse? {
       return UserQuizApi().submitUserQuiz(userQuiz)
    }

    fun validateAnswer(
        responsesList: MutableMap<String, String>,
        questions: List<Question>?): Map<String, String> {
        var count = 0
        var total = 0

        var map = mutableMapOf<String, String>()
        questions?.forEach {
            if(responsesList[it.question] == it.answer && it.assertions?.size != 0) {
                count ++
            }

            if(it.assertions?.size != 0) {
                total ++
            }
        }
        map[count.toString()] =  total.toString()

        return map
    }

    var course by mutableStateOf<CourseResponse?>(null)
        private set

    var questions by mutableStateOf(emptyList<Question>().toMutableList())
        private set
}