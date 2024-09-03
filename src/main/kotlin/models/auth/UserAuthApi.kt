package models.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.auth.UserRetrofitClient.getApiService
import models.video.VideoRetrofitClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object UserRetrofitClient {

    private const val BASE_URL = "https://streaming-app-3nf3.onrender.com/"

    private val retrofitBuilder = Retrofit.Builder()

    private val okHttpClient = OkHttpClient.Builder()

    fun UserRetrofitClient.setInterceptor(tenantId: String): UserRetrofitClient {
        okHttpClient.addInterceptor { chain ->
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

    fun UserRetrofitClient.getApiService(): UserAuthUrls {
        return  retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAuthUrls::class.java)
    }
}

class UserAuthApi {

    suspend fun loginWithPhone(phoneNumber: String): TokenResponse {
        return try {
            withContext(Dispatchers.IO) {
                val token = UserRetrofitClient
                    .getApiService()
                    .loginWithPhone(PhoneComponent(phoneNumber))

                if(token != null){
                    TokenResponse(token)
                }
                else {
                    TokenResponse(null, TokenError(0,
                        "Token is null"))
                }
            }
        } catch (exception: Exception) {
            TokenResponse(null, TokenError(0,
                exception.message?:"exception"))
        }
    }
}