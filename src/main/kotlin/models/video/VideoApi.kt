package models.video


import helpers.StorageHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.*
import models.video.VideoRetrofitClient.getApiService
import models.video.VideoRetrofitClient.setInterceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object VideoRetrofitClient {

    private const val BASE_URL = "https://streaming-app-3nf3.onrender.com/"

    private val retrofitBuilder = Retrofit.Builder()

    private val okHttpClient = OkHttpClient.Builder()

    private val apiService: VideoUrls by lazy {
        retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideoUrls::class.java)
    }

    fun VideoRetrofitClient.setInterceptor(tenantId: String): VideoRetrofitClient {
        okHttpClient.addInterceptor {chain ->
            val request = chain
                .request()
                .newBuilder()
                .header("API_KEY", "123rTepRzooroBlackBill321")
                .header("tenantId", tenantId)
                .build()

            return@addInterceptor chain.proceed(request)
        }
        retrofitBuilder.client(okHttpClient.build())

        return this
    }

    fun VideoRetrofitClient.getApiService(): VideoUrls {
        return  retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideoUrls::class.java)
    }
}


class VideoApi {

    suspend fun createTopic(
        file: File,
        name: String,
        courseDescription: String,
        module: String,
        video: String,
        description: String,
        instructorName: String,
        ): UpLoadVideoResponse? {

        // Prepare the file part
        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        // Prepare the name part
        val topicName = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val topicDescription = courseDescription.toRequestBody("text/plain".toMediaTypeOrNull())
        val moduleName = module.toRequestBody("text/plain".toMediaTypeOrNull())
        val videoName = video.toRequestBody("text/plain".toMediaTypeOrNull())
        val videoDescription = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val instructor = instructorName.toRequestBody("text/plain".toMediaTypeOrNull())

        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)
                val response = VideoRetrofitClient
                    .setInterceptor(authCode!!)
                    .getApiService()
                    .uploadTopicVideo(body, topicName, topicDescription,  moduleName, videoName, videoDescription, instructor)

                UpLoadVideoResponse(response)
            }
        } catch (exception: Exception) {
            // Handle the exception if needed, and return null
            UpLoadVideoResponse(null, ErrorComponent(0, exception.message?:"exception"))
        }
    }

    suspend fun addVideoAndModule( file: File,
                             name: String,
                             module: String,
                             video: String,
                             description: String):  UpLoadVideoResponse ? {

        // Prepare the file part
        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        // Prepare the name part
        val topicId = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val moduleName = module.toRequestBody("text/plain".toMediaTypeOrNull())
        val videoName = video.toRequestBody("text/plain".toMediaTypeOrNull())
        val videoDescription = description.toRequestBody("text/plain".toMediaTypeOrNull())

        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

                val response = VideoRetrofitClient
                    .setInterceptor(authCode!!)
                    .getApiService()
                    .addVideoAndModule(body, topicId, moduleName, videoName, videoDescription)

                val thisVideo = response?.modules
                    ?.first { it.name == module }
                    ?.videos
                    ?.first { it.description == description }

                UpLoadVideoResponse(thisVideo)
            }
        } catch (exception: Exception) {
            // Handle the exception if needed, and return null
            UpLoadVideoResponse(null, ErrorComponent(0, exception.message?:"exception"))
        }
    }

    suspend fun addOrUpdateQuiz(quizRequest: QuizRequest): CourseResponse? {

        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

                val response = VideoRetrofitClient
                    .setInterceptor(authCode!!)
                    .getApiService()
                    .addOrModifyQuiz(quizRequest)

                CourseResponse(courses = response)
            }
        } catch (exception: Exception) {
            CourseResponse(errorComponent = ErrorComponent(errorCode = 100, errorMessage = exception.message.toString()))
        }
    }

    suspend fun addNewVideoInModule( file: File,
                                     courseId: String,
                                     moduleRefId: String,
                                     video: String,
                                     description: String):  UpLoadVideoResponse ? {
        // Prepare the file part
        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        // Prepare the name part
        val topicId = courseId.toRequestBody("text/plain".toMediaTypeOrNull())
        val moduleId = moduleRefId.toRequestBody("text/plain".toMediaTypeOrNull())
        val videoName = video.toRequestBody("text/plain".toMediaTypeOrNull())
        val videoDescription = description.toRequestBody("text/plain".toMediaTypeOrNull())

        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

                val response = VideoRetrofitClient
                    .setInterceptor(authCode!!)
                    .getApiService()
                    .addNewVideoInModule(body, topicId, moduleId, videoName, videoDescription)

                val thisVideo = response?.modules
                    ?.first { it._id == moduleRefId }
                    ?.videos
                    ?.first { it.description == description }

                UpLoadVideoResponse(thisVideo)
            }
        } catch (exception: Exception) {
            // Handle the exception if needed, and return null
            UpLoadVideoResponse(null, ErrorComponent(0, exception.message?:"exception"))
        }
    }

    suspend fun updateVideo( file: File,
                                  name: String,
                                  module: String,
                                  videoId: String,
                                  referenceId: String,
                                  video: String,
                                  description: String):  UpLoadVideoResponse ? {

        // Prepare the file part
        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        // Prepare the name part
        val topicId = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val moduleId = module.toRequestBody("text/plain".toMediaTypeOrNull())
        val vidId = videoId.toRequestBody("text/plain".toMediaTypeOrNull())
        val oldVidId = referenceId.toRequestBody("text/plain".toMediaTypeOrNull())
        val videoName = video.toRequestBody("text/plain".toMediaTypeOrNull())
        val videoDescription = description.toRequestBody("text/plain".toMediaTypeOrNull())

        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

                val response = VideoRetrofitClient
                    .setInterceptor(authCode!!)
                    .getApiService()
                    .updateTopicVideo(body, topicId, moduleId, vidId, oldVidId, videoName, videoDescription)

                val thisVideo = response?.modules
                    ?.first { it._id == module }
                    ?.videos
                    ?.first { it.description == description }

                UpLoadVideoResponse(thisVideo)
            }
        } catch (exception: Exception) {
            // Handle the exception if needed, and return null
            UpLoadVideoResponse(null, ErrorComponent(0, exception.message?:"exception"))
        }
    }


    suspend fun updateTopicImage( file: File, topic: String):  UpdateUrl? {

        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val topicId = topic.toRequestBody("text/plain".toMediaTypeOrNull())

        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

                val response = VideoRetrofitClient
                    .setInterceptor(authCode!!)
                    .getApiService()
                    .setTopicImage(body, topicId)

                response
            }
        } catch (exception: Exception) {
            exception.message?.let { UpdateUrl(it) }
        }
    }

    suspend fun fetchCourses(): CoursesListResponse {
        return try {
            //List<VideoComponent>?
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)
                CoursesListResponse(
                    VideoRetrofitClient
                        .setInterceptor(authCode!!)
                        .getApiService()
                        .getCourses()
                )
            }
        } catch (exception: Exception) {
            CoursesListResponse(null, ErrorComponent(0, exception.message?:"exception"))
        }
    }

    suspend fun fetchVideos(): VideosListResponse {
        return try {
            //List<VideoComponent>?
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

                VideosListResponse(
                    VideoRetrofitClient
                        .setInterceptor(authCode!!)
                        .getApiService()
                        .getVideos()
                )
            }
        } catch (exception: Exception) {
            VideosListResponse(null, ErrorComponent(0, exception.message?:"exception"))
        }
    }

    suspend fun fetchVideosByCourseId(courseId: String): VideosListResponse {
        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)
                VideosListResponse(
                    VideoRetrofitClient
                        .setInterceptor(authCode!!)
                        .getApiService()
                        .getCourseVideos(courseId)
                )
            }
        } catch (exception: Exception) {
            VideosListResponse(null, ErrorComponent(0, exception.message?:"exception"))
        }
    }
}

