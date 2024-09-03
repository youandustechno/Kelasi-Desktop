package ui.dashboards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.CoursesListResponse
import models.video.VideoApi

class DashboardViewModel: ViewModel()

open class ViewModel {

    var coursesList by mutableStateOf<CoursesListResponse?>(null)
        private set

    suspend fun getCoursesList(): CoursesListResponse? {
        coursesList = VideoApi().fetchCourses()
        return coursesList
    }
}