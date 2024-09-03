package models.video

import models.UpdateUrl
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface VideoUrls {

    @GET("/courses")
    suspend fun getVideos(
       // @Header("tenantId") tenant: String
    ): List<VideoComponent>?

    @GET("/courses/{id}")
    suspend fun getCourseVideos(
        //@Header("tenantId") tenant: String,
        @Path("id") id: String
    ): List<VideoComponent>?

    @Multipart
    @POST("/topics")
    suspend fun uploadTopicVideo(
        //@Header("tenantId") tenant: String,
        @Part file: MultipartBody.Part,
        @Part("topic") topic: RequestBody?,
        @Part("topicDescription") topicDescription: RequestBody?,
        @Part("module") module: RequestBody?,
        @Part("video") video: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("instructor") instructor: RequestBody?
    ): VideoComponent?

    @GET("/topics")
    suspend fun getCourses(): List<CourseComponent>?

    @Multipart
    @PUT("/topics/video")
    suspend fun addNewVideoInModule(
        //@Header("tenantId") tenant: String,
        @Part file: MultipartBody.Part,
        @Part("topicId") topic: RequestBody?,
        @Part("moduleId") module: RequestBody?,
        @Part("videoName") video: RequestBody?,
        @Part("description") description: RequestBody?
    ): CourseComponent?

    @Multipart
    @PUT("/topics/image")
    suspend fun setTopicImage(
        //@Header("tenantId") tenant: String,
        @Part file: MultipartBody.Part,
        @Part("topicId") topic: RequestBody?
    ): UpdateUrl?

    @Multipart
    @PUT("/topics/update")
    suspend fun updateTopicVideo(
        //@Header("tenantId") tenant: String,
        @Part file: MultipartBody.Part,
        @Part("topicId") topic: RequestBody?,
        @Part("moduleId") module: RequestBody?,
        @Part("videoId") videoId: RequestBody?,
        @Part("publicId") referenceId: RequestBody?,
        @Part("videoName") video: RequestBody?,
        @Part("description") description: RequestBody?
    ): CourseComponent?


    @Multipart
    @PUT("/topics/module")
    suspend fun addVideoAndModule(
        //@Header("tenantId") tenant: String,
        @Part file: MultipartBody.Part,
        @Part("topicId") topic: RequestBody?,
        @Part("moduleName") module: RequestBody?,
        @Part("videoName") video: RequestBody?,
        @Part("description") description: RequestBody?,
    ): CourseComponent?


    @PUT("/topics/quiz")
    suspend fun addOrModifyQuiz(
        //@Header("tenantId") tenant: String,
        @Body quiz: QuizRequest): CourseComponent?
}