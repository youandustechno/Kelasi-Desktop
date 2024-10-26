package ui

import models.auth.Score
import models.auth.UserDataModel
import models.video.CourseComponent

object Cache {

    //list of courses available for an user.
    var courseCache: List<CourseComponent>? = null

    //current user information
    var userCache : UserDataModel? = null


//    var _id: String = "",
//    var firstName: String,
//    var lastName: String,
//    var middleName: String = "",
//    var level: String = "",
//    var email: String = "",
//    var phoneNumber:String = "",
//    var password: String = "",
//    var confirmPassword: String = "",
//    val url: String= "",
//    var scores: List<Score>? = null,
//    var isApproved: Boolean? = false

    fun updateUser(user: UserDataModel) {
        userCache?._id = user._id
        userCache?.level = user.level
        userCache?.email = user.email
        userCache?.firstName = user.firstName
        userCache?.lastName = user.lastName
        userCache?.middleName = user.middleName
        userCache?.phoneNumber = user.phoneNumber
    }
}