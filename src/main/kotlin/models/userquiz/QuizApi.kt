package models.userquiz

import helpers.StorageHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.BaseValues.BASE_URL
import models.ErrorComponent
import models.UserQuizResponse
import models.userquiz.QuizRetrofitClient.getApiService
import models.userquiz.QuizRetrofitClient.setInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuizRetrofitClient {

    private val retrofitBuilder = Retrofit.Builder()

    private val okHttpClient = OkHttpClient.Builder()

    private val apiService: QuizUrls by lazy {
        retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizUrls::class.java)
    }

    fun QuizRetrofitClient.setInterceptor(tenantId: String): QuizRetrofitClient{
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

    fun QuizRetrofitClient.getApiService(): QuizUrls {
        return  retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizUrls::class.java)
    }
}

class UserQuizApi {

    suspend fun submitUserQuiz(userQuizComponent: UserScoreData): UserQuizResponse {

        return try {
            withContext(Dispatchers.IO) {

                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

                val response = QuizRetrofitClient
                    .setInterceptor(authCode!!)
                    .getApiService()
                    .submitUserQuiz(userQuizComponent)

                UserQuizResponse(userScores = response)
            }
        } catch (e:Exception) {
            UserQuizResponse(errorComponent = ErrorComponent(0, e.message?:"exception"))
        }

    }
}