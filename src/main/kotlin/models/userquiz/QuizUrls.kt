package models.userquiz

import models.UserQuizResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface QuizUrls {

    @POST("quiz/submit")
    suspend fun submitUserQuiz(@Body userQuiz: UserQuizComponent): UserQuizResponse?
}
