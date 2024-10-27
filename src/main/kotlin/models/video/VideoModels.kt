package models.video

import com.google.gson.annotations.SerializedName
import ui.NavKeys.EMPTY
import ui.videos.VideoState


data class CourseComponent(
    var _id: String? = null,
    var cloudinaryId: String = EMPTY,
    var instructor: String = EMPTY,
    var name: String = EMPTY,
    var url: String = EMPTY,
    var title: String = EMPTY,
    var modules: List<Module> = listOf(),
    var level : List<String>? = null,
    var description: String = EMPTY
)

data class VideoComponent(
    var _id: String? = null,
    var url: String = EMPTY,
    @SerializedName("cloudinary_id")
    var referenceId: String?= null,
    var title: String = EMPTY,
    var description: String = EMPTY,
    var module: String = EMPTY,
    var course: String = EMPTY,
    var playerState: VideoState = VideoState.NONE)

data class Module(
    var _id: String? = null,
    var cloudinaryId: String = EMPTY,
    var name: String = EMPTY,
    var url: String = EMPTY,
    var videos: List<VideoComponent> = listOf(),
    var documents: List<DocumentComponent> = listOf(),
    var quiz: List<QuizComponent> = listOf(),
    var description: String = EMPTY
)

data class QuizComponent(
    var _id: String? = null,
    var topicname: String = EMPTY,
    var title: String = EMPTY,
    var modulename: String = EMPTY,
    var problems: List<Question>? = null,
    var time: Int = 0
)

data class Question(
    var question: String = EMPTY,
    var answer: String = EMPTY,
    var assertions: List<String>? = null,
    var positon: Int = 0
)


//Special
data class QuizRequest(
    var topicId: String = EMPTY,
    var moduleId: String = EMPTY,
    var moduleName: String = EMPTY,
    var quizId: String? = null,
    var isUpdate: Boolean? = null,
    var quiz: QuizComponent? = null
)

data class DocumentComponent(
    var _id: String? = null,
    var url: String = EMPTY,
    @SerializedName("cloudinary_id")
    var referenceId: String?= null,
    var title: String = EMPTY,
    var path: String = EMPTY,
    var description: String = EMPTY,
    var module: String = EMPTY,
    var courseId: String = EMPTY)

data class DocumentFieldsComponent(
    var _id: String? = null,
    var title: String = EMPTY,
    var description: String = EMPTY,
    var module: String = EMPTY,
    var courseId: String = EMPTY)