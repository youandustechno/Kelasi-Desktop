package models.userquiz


data class UserQuizComponent (
    var _id: String = "",
    val firstName: String,
    val lastName: String,
    var middleName: String ="",
    var grade: Int = 0,
    var total: Int,
    var quizId: String,
    var topicName: String = "",
    var moduleName: String = "",
    var topicId: String,
    var response: List<String>? = null,
    var hasResponseField: Boolean = false
)

data class ScoreData(var grade:String, val total: String)

