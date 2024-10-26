package ui.quizzes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.CourseResponse
import models.UserQuizResponse
import models.userquiz.UserQuizApi
import models.userquiz.UserQuizComponent
import models.video.*
import ui.dashboards.ViewModel

class QuizViewModel: ViewModel() {

    suspend fun submitQuiz(userQuiz: UserQuizComponent) : UserQuizResponse? {
       return UserQuizApi().submitUserQuiz(userQuiz)
    }

    var course by mutableStateOf<CourseResponse?>(null)
        private set

    var questions by mutableStateOf(emptyList<Question>().toMutableList())
        private set
}