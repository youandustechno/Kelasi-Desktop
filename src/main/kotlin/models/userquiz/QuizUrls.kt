package models.userquiz

import models.UserQuizResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface QuizUrls {

    @POST("users/quiz")
    suspend fun submitUserQuiz(@Body userQuiz: UserQuizComponent): UserQuizResponse?
}
