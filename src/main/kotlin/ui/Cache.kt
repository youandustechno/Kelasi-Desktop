package ui

import models.auth.Score
import models.auth.UserDataModel
import models.video.CourseComponent
import ui.NavKeys.EMPTY

object Cache {

    //list of courses available for an user.
    var courseCache: List<CourseComponent>? = null

    //current user information
    var userCache : UserDataModel? = null

    var authCode: String = EMPTY

    fun updateUser(user: UserDataModel) {
        userCache?._id = user._id?:EMPTY
        userCache?.level = user.level
        userCache?.email = user.email
        userCache?.firstName = user.firstName?: EMPTY
        userCache?.lastName = user.lastName?: EMPTY
        userCache?.middleName = user.middleName?: EMPTY
        userCache?.phoneNumber = user.phoneNumber?: EMPTY
        userCache?.url = user.url?: EMPTY
        userCache?.urlId = user.urlId?: EMPTY
    }
}