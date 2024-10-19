package models.auth

import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Part ("firstName") firstName : RequestBody?,
        @Part ("lastName") lastName : RequestBody?,
        @Part ("middleName") middleName : RequestBody?,
        @Part ("email") email : RequestBody?,
        @Part ("level") level : RequestBody?,
        @Part ("phoneNumber") phoneNumber : RequestBody?,
        @Part ("isApproved") isApproved : RequestBody?
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