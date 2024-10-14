package models.auth

import okhttp3.MultipartBody
import retrofit2.http.*

interface UserAuthUrls {

//    @GET("/auths/{organizationId}")
//    suspend fun getAuthCode(@Path("organizationId") id: String): OrganizationComponent?

    @POST("/auths")
    suspend fun loginWithPhone(
        @Body phoneNumber: PhoneComponent
    ): TokenComponent?


    @POST("/users/register")
    suspend fun registerUser(
        @Body user: UserDataModel
    ): UserDataModel?

    @Multipart
    @POST("/users/picture")
    suspend fun registerUserWithPic(
        @Part file: MultipartBody.Part,
        @Body user: UserDataModel
    ): UserDataModel?

    @PATCH("/users/modify")
    suspend fun updaterUser(
        @Body user: UserDataModel
    ): UserDataModel?

    @Multipart
    @PUT("/users/{userId}")
    suspend fun updaterUserWithPic(
        @Part file: MultipartBody.Part,
        @Path("userId") id: String,
        @Body user: UserDataModel
    ): UserDataModel?
}