package models.group

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.AuthCodeResponse
import models.ErrorComponent
import models.group.GroupRetrofitClient.getApiService
import models.group.GroupRetrofitClient.setInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object GroupRetrofitClient {

    private const val BASE_URL = "https://streaming-app-3nf3.onrender.com/"

    private val okHttpClient = OkHttpClient.Builder()

    private val retrofitBuilder = Retrofit.Builder()

    fun GroupRetrofitClient.setInterceptor(tenantId: String): GroupRetrofitClient {
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

    fun GroupRetrofitClient.getApiService(): GroupUrls {
        return  retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupUrls::class.java)
    }
}

class GroupApi {

    suspend fun verifyTenant(text: String): AuthCodeResponse {
        return try {
            withContext(Dispatchers.IO) {
                val response = GroupRetrofitClient
                    .setInterceptor(text)
                    .getApiService()
                    .getAuthCode(Date().time.toString())

                AuthCodeResponse(response)
            }
        } catch (exception: Exception) {
            AuthCodeResponse(null, ErrorComponent(0, exception.message?:"exception"))
        }
    }
}