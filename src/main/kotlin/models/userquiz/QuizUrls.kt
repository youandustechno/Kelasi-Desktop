package models.userquiz

import models.UserQuizResponse
import retrofit2.http.*


interface QuizUrls {

    @POST("quizzes/first")
    suspend fun firstUserQuiz(@Body userQuiz: UserScoreData): UserQuizComponent?

    @PUT("quizzes/submit")
    suspend fun submitUserQuiz(@Body userQuiz: UserScoreData): UserQuizComponent?
}
