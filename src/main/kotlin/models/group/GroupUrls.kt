package models.group


import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupUrls {

    @GET("/organizations/{organizationId}")
    suspend fun getAuthCode(@Path("organizationId") id: String): AuthResponseComponent?

    @GET("/organizations/{organizationId}")
    suspend fun postOrganization(
        //@Header("tenantId") tenant: String,
        @Body id: String
    ): String?
}