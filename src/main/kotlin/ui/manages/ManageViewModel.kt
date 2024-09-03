package ui.manages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.CoursesListResponse
import models.UpLoadVideoResponse
import models.video.*
import java.io.File
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

class ManageViewModel{

    var filePath by mutableStateOf<String?>(null)
        private set

    var uploadResult by mutableStateOf<String?>(null)
        private set

    fun selectFile(path: String) {
        filePath = path
    }

    var coursesList by mutableStateOf<CoursesListResponse?>(null)
        private set

    suspend fun getCoursesList(): CoursesListResponse? {
        coursesList = VideoApi().fetchCourses()
        return coursesList
    }

    /**
     * Upload File
     */
    suspend fun uploadFile(
        courseName: String,
        moduleName: String,
        videoTitle: String,
        videoDescription: String,
        videoComponent: VideoComponent?
    ): UpLoadVideoResponse? {

        return filePath?.let { path ->
            val uploadVideo = VideoApi()
            val file = File(path)
            val date = Date().time.milliseconds.toString()

            uploadVideo.updateVideo(file,
                courseName,
                moduleName,
                videoComponent?._id ?: date,
                videoComponent?.referenceId ?: "",
                videoTitle,
                videoDescription)

        }
    }

    /**
     * Add a new Video into a Module.
     */
    suspend fun addNewVideoInModule(
        courseId: String,
        moduleId: String,
        videoTitle: String,
        videoDescription: String): UpLoadVideoResponse? {

        return filePath?.let { path ->
            val uploadVideo = VideoApi()
            val file = File(path)

            uploadVideo.addNewVideoInModule(
                file,
                courseId,
                moduleId,
                videoTitle,
                videoDescription
            )

        }
    }

    /**
     * Add Video and Module
     */
    suspend fun addVideoFileAndModule(
        courseId: String,
        moduleName: String,
        videoTitle: String,
        videoDescription: String
    ): UpLoadVideoResponse? {

        return filePath?.let { path ->
            val uploadVideo = VideoApi()
            val file = File(path)
            uploadVideo
                .addVideoAndModule(
                    file,
                    courseId,
                    moduleName,
                    videoTitle,
                    videoDescription
                )

        }
    }

    /**
     * Create topic
     */
    suspend fun createTopic(
        instructorName: String,
        courseName: String,
        courseDescription: String,
        moduleName: String,
        videoTitle: String,
        videoDescription: String
    ): UpLoadVideoResponse? {

        return filePath?.let { path ->
            val uploadVideo = VideoApi()
            val file = File(path)

            uploadVideo.createTopic(
                file,
                courseName,
                courseDescription,
                moduleName,
                videoTitle,
                videoDescription,
                instructorName
            )

        }
    }
}