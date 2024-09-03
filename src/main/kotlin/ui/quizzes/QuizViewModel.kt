package ui.quizzes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.CourseResponse
import models.video.*
import ui.dashboards.ViewModel

class QuizViewModel: ViewModel() {

    var course by mutableStateOf<CourseResponse?>(null)
        private set

    var questions by mutableStateOf(emptyList<Question>().toMutableList())
        private set

    suspend fun addOrUpdateQuiz(
        courseComponent: CourseComponent,
        moduleId: String,
        quizId: String?,
        timeEntry: String =""): CourseResponse? {

        val module= courseComponent.modules.find { it._id == moduleId }
        val isUpdate = !quizId.isNullOrEmpty()

        val time = if(timeEntry.isNotEmpty()) {
            timeEntry.trim().toInt()
        } else {
            0
        }

        val quiz = if(isUpdate) {
            QuizComponent(
                _id = quizId,
                topicname = courseComponent.name,
                modulename = module?.name!!,
                problems = questions,
                time = time
            )
        }  else {
            QuizComponent(
                _id = null,
                topicname = courseComponent.name,
                modulename = module?.name!!,
                problems = questions,
                time = time
            )
        }

        val quizRequest = QuizRequest(
            topicId = courseComponent._id!!,
            moduleId = module._id!!,
            moduleName = module.name,
            quizId = quizId,
            isUpdate = isUpdate,
            quiz = quiz
        )

        course = VideoApi().addOrUpdateQuiz(quizRequest)

        return course
    }

    fun validateFields(questionEntry: String,
                       assertionEntryOne: String,
                       assertionEntryTwo: String,
                       assertionEntryThree: String,
                       assertionEntryFour: String,
                       assertionEntryFive: String) : Boolean {

        if(questionEntry.isEmpty()) return false
        if(assertionEntryOne.isEmpty()) return false
        if(assertionEntryTwo.isEmpty()) return false
        if(assertionEntryThree.isEmpty()) return false
        if(assertionEntryFour.isEmpty()) return false
        if(assertionEntryFive.isEmpty()) return false

        return true
    }

    fun addQuestion(question: Question) {
       questions.add(question)
    }
}