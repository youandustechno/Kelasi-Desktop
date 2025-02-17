package models.auth

import models.UserValues
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UserAuthUrls {

//    @GET("/auths/{organizationId}")
//    suspend fun getAuthCode(@Path("organizationId") id: String): OrganizationComponent?

    @POST("/auths/phone")
    suspend fun loginWithPhone(
        @Body phoneNumber: PhoneComponent
    ): Response<TokenComponent>?

    @POST("/auths/email")
    suspend fun loginWithEmail(
        @Body credentials: EmailAndPassComponent
    ): Response<TokenComponent>?

    @POST("/auths/register")
    suspend fun register(
        @Body credentials: EmailAndPassComponent
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
        @Path("userId") id: String,
        @Part file: MultipartBody.Part,
        @Part ("firstName") firstName : RequestBody?,
        @Part ("lastName") lastName : RequestBody?,
        @Part ("middleName") middleName : RequestBody?,
        @Part ("email") email : RequestBody?,
        @Part ("level") level : RequestBody?,
        @Part ("phoneNumber") phoneNumber : RequestBody?,
        @Part ("isApproved") isApproved : RequestBody?,
    ): UserDataModel?

    @POST("/auths/password")
    suspend fun updateUserPassword(
        @Body passwordModel: ChangePasswordModel
    ): UserDataModel?

    @POST("users/get")
    suspend fun getUser(@Body userValues: UserValues) : UserDataModel?

    @POST("users/recover")
    suspend fun recoverUser(@Body userValues: UserCredentials) : UserDataModel?

    @POST("auths/logout")
    suspend fun logout(@Body userValues: TokenComponent) : LogoutSuccess?
}