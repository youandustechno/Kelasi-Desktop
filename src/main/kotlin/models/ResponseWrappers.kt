package models

import com.google.gson.annotations.SerializedName
import models.group.AuthResponseComponent
import models.group.OrganizationComponent
import models.userquiz.Answer
import models.userquiz.UserQuizComponent
import models.video.CourseComponent
import models.video.VideoComponent
import ui.NavKeys.EMPTY

//Error Response
data class ErrorComponent(val errorCode: Int = 0, val errorMessage: String? = null)

data class AuthCodeResponse(var auth: AuthResponseComponent?= null, var error: ErrorComponent?= null)

//Single Video Response
data class UpLoadVideoResponse(val uploaded: VideoComponent? = null, val errorComponent: ErrorComponent? = null)

//List of Video Response
data class VideosListResponse(val videos:List<VideoComponent>?= null, val errorComponent: ErrorComponent? = null)

data class CoursesListResponse(val courses:List<CourseComponent>?= null, val errorComponent: ErrorComponent? = null)

data class CourseResponse(val courses: CourseComponent?= null, val errorComponent: ErrorComponent? = null)

data class UpdateUrl(val updatedTopicUrl: String = EMPTY)

data class UserQuizResponse(val userScores: UserQuizComponent?= null, val errorComponent: ErrorComponent? = null)

data class QuizzesResponse(val cours: Subject?= null, val errorComponent: ErrorComponent? = null)

data class UserValues(val id: String)

//Quiz Response
data class Subject (
    var userRef: String,
    var topicName: String = EMPTY,
    var topicId: String = EMPTY,
    var level: String = EMPTY,
    @SerializedName("scores")
    var scores:List<QuizScore>)

data class QuizScore (
    @SerializedName("quiz_ref")
    var quizRef: String,
    var score: String,
    var total: String,
    var module: String,
    var responses: List<Answer>,
    var hasResponseField: String,
    var pending: String,
)
