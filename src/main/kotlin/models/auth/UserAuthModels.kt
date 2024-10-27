package models.auth

import ui.NavKeys.EMPTY

data class TokenComponent(val token: String = EMPTY)

data class PhoneComponent(val phoneNumber: String = EMPTY)

data class EmailAndPassComponent(var email: String = EMPTY, val password: String, val phoneNumber: String = EMPTY)

data class TokenResponse(val token: TokenComponent? = null, val error: TokenError? = null)

data class TokenError(val code: Int = 0, val error: String = EMPTY)

data class UserResponse(val user: UserDataModel? = null, val error: TokenError? = null)

data class UserDataModel(
    var _id: String = EMPTY,
    var firstName: String,
    var lastName: String,
    var middleName: String = EMPTY,
    var level: String = EMPTY,
    var email: String = EMPTY,
    var phoneNumber:String = EMPTY,
    var password: String = EMPTY,
    var confirmPassword: String = EMPTY,
    var url: String? = EMPTY,
    var urlId: String? = EMPTY,
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
