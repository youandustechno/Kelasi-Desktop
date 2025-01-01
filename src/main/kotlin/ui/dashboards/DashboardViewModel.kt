package ui.dashboards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.CourseResponse
import models.CoursesListResponse
import models.ErrorComponent
import models.video.VideoApi

class DashboardViewModel: ViewModel()

open class ViewModel {

    var coursesList by mutableStateOf<CoursesListResponse?>(null)
        private set

    var course by mutableStateOf<CourseResponse?>(null)
        private set

    suspend fun getCoursesList(): CoursesListResponse? {
        coursesList = VideoApi().fetchCourses()
        return coursesList
    }

    suspend fun getCourse(id: String) : CourseResponse? {
        val courseObject  = VideoApi().fetchCourses()
        val theCourse = try {
            courseObject.courses?.first { it._id == id }
        } catch (ex: Exception) {
            null
        }

        if(theCourse == null) {
            course = CourseResponse(null,
                ErrorComponent(0, "Unknown Error")
            )
        } else {
            course = CourseResponse(courses = theCourse)
        }

        return course
    }
}