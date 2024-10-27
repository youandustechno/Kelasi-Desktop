package models

import models.group.OrganizationComponent
import models.userquiz.UserQuizComponent
import models.video.CourseComponent
import models.video.VideoComponent
import ui.NavKeys.EMPTY

//Error Response
data class ErrorComponent(val errorCode: Int = 0, val errorMessage: String? = null)

data class AuthCodeResponse(var org: OrganizationComponent?= null, var error: ErrorComponent?= null)

//Single Video Response
data class UpLoadVideoResponse(val uploaded: VideoComponent? = null, val errorComponent: ErrorComponent? = null)

//List of Video Response
data class VideosListResponse(val videos:List<VideoComponent>?= null, val errorComponent: ErrorComponent? = null)

data class CoursesListResponse(val courses:List<CourseComponent>?= null, val errorComponent: ErrorComponent? = null)

data class CourseResponse(val courses: CourseComponent?= null, val errorComponent: ErrorComponent? = null)

data class UpdateUrl(val updatedTopicUrl: String = EMPTY)

data class UserQuizResponse(val userScores: UserQuizComponent?= null, val errorComponent: ErrorComponent? = null)

data class UserValues(val id: String)