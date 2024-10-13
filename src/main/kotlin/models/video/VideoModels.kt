package models.video

import com.google.gson.annotations.SerializedName
import ui.videos.VideoState


data class CourseComponent(
    var _id: String? = null,
    var cloudinaryId: String ="",
    var instructor: String ="",
    var name: String = "",
    var url: String ="",
    var title: String ="",
    var modules: List<Module> = listOf(),
    var description: String =""
)

data class VideoComponent(
    var _id: String? = null,
    var url: String = "",
    @SerializedName("cloudinary_id")
    var referenceId: String?= null,
    var title: String = "",
    var description: String = "",
    var module: String = "",
    var course: String = "",
    var playerState: VideoState = VideoState.NONE)

data class Module(
    var _id: String? = null,
    var cloudinaryId: String = "",
    var name: String = "",
    var url: String = "",
    var videos: List<VideoComponent> = listOf(),
    var documents: List<DocumentComponent> = listOf(),
    var quiz: List<QuizComponent> = listOf(),
    var description: String =""
)

data class QuizComponent(
    var _id: String? = null,
    var topicname: String = "",
    var modulename: String = "",
    var problems: List<Question>? = null,
    var time: Int = 0
)

data class Question(
    var question: String = "",
    var answer: String = "",
    var assertions: List<String>? = null,
    var positon: Int = 0
)


//Special
data class QuizRequest(
    var topicId: String = "",
    var moduleId: String = "",
    var moduleName: String = "",
    var quizId: String? = null,
    var isUpdate: Boolean? = null,
    var quiz: QuizComponent? = null
)

data class DocumentComponent(
    var _id: String? = null,
    var url: String = "",
    @SerializedName("cloudinary_id")
    var referenceId: String?= null,
    var title: String = "",
    var path: String = "",
    var description: String = "",
    var module: String = "",
    var courseId: String = "")

data class DocumentFieldsComponent(
    var _id: String? = null,
    var title: String = "",
    var description: String = "",
    var module: String = "",
    var courseId: String = "")