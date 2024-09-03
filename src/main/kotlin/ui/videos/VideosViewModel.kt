package ui.videos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.UpdateUrl
import models.VideosListResponse
import models.video.*
import ui.dashboards.ViewModel
import java.io.File


class VideosViewModel : ViewModel() {

    var listOfVideos by mutableStateOf<List<VideoComponent?>>(emptyList())
        private set

    var currentVideo by mutableStateOf<VideoComponent?>(null)
        private set

    var filePath by mutableStateOf<String?>(null)
        private set

    private val videoApi = VideoApi()

    var uploadResult by mutableStateOf<String?>(null)
        private set

    suspend fun getVideos(): VideosListResponse {
        return videoApi.fetchVideos()
    }

    suspend fun getVideosById(courseId:String) : VideosListResponse {
        return videoApi.fetchVideosByCourseId(courseId)
    }

    fun selectImageFile(path: String) {
        filePath = path
    }

    suspend fun uploadImageFile(courseId: String): UpdateUrl? {

        return filePath?.let { path ->
            val uploadVideo = VideoApi()
            val file = File(path)

            uploadVideo.updateTopicImage(file, courseId)
        }
    }
}