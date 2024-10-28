package models.userquiz

import ui.NavKeys.EMPTY


data class UserQuizComponent (
    var _id: String = EMPTY,
    var userRef: String = EMPTY,
    val firstName: String,
    val lastName: String,
    var middleName: String = EMPTY,
    var grade: Int = 0,
    var total: Int,
    var quizId: String,
    var topicName: String = EMPTY,
    var moduleName: String = EMPTY,
    var topicId: String,
    var response: List<String>? = null,
    var hasResponseField: Boolean = false
)

data class ScoreInfo (
    var quizRef: String,
    var score: String,
    var total: String,
    var module: String,
    var topicName: String,
    var created: String,
    var topicRef: String,
    var response: Map<String, String>? = null,
    var hasResponseField: Boolean = false,
    var pending: Boolean? = null
)

data class UserScoreData(
    var userRef: String,
    val firstName: String,
    val lastName: String,
    var middleName: String = EMPTY,
    var level: String,
    val scoreInfo: ScoreInfo
)

