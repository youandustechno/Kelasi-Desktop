package models.auth

data class TokenComponent(val token: String = "")

data class PhoneComponent(val phoneNumber: String = "")

data class EmailAndPassComponent(var email: String = "", val password: String, val phoneNumber: String ="")

data class TokenResponse(val token: TokenComponent? = null, val error: TokenError? = null)

data class TokenError(val code: Int = 0, val error: String = "")

data class UserResponse(val user: UserDataModel? = null, val error: TokenError? = null)

data class UserDataModel(
    var _id: String = "",
    var firstName: String,
    var lastName: String,
    var middleName: String = "",
    var level: String = "",
    var email: String = "",
    var phoneNumber:String = "",
    var password: String = "",
    var confirmPassword: String = "",
    val url: String= "",
    var scores: List<Score>? = null,
    var isApproved: Boolean? = false
)

data class Score(
    var quizNumber: String,
    var gradle: String,
    var total: String,
    var topicName: String,
    var topicId: String,
    var moduleId: String)
