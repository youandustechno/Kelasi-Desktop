package models.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface UserAuthUrls {

//    @GET("/auths/{organizationId}")
//    suspend fun getAuthCode(@Path("organizationId") id: String): OrganizationComponent?

    @POST("/auths")
    suspend fun loginWithPhone(
        @Body phoneNumber: PhoneComponent
    ): TokenComponent?
}