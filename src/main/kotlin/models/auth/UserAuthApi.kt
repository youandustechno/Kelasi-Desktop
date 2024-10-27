package models.auth

import helpers.StorageHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.BaseValues.BASE_URL
import models.UserValues
import models.auth.UserRetrofitClient.getApiService
import models.auth.UserRetrofitClient.setInterceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ui.Cache
import java.io.File


object UserRetrofitClient {

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

    suspend fun loginWithEmail(credentials: EmailAndPassComponent) : TokenResponse {
        return try {
            withContext(Dispatchers.IO) {
                val token = UserRetrofitClient
                    .getApiService()
                    .loginWithEmail(credentials)

                if(token != null) {
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

    suspend fun registerAccessCredentials(credentials: EmailAndPassComponent) : TokenResponse {
        return try {
            withContext(Dispatchers.IO) {
                val token = UserRetrofitClient
                    .getApiService()
                    .register(credentials)

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

    suspend fun registerUser(file: File?, userData: UserDataModel): UserResponse {

        val theFile = if(file != null) {
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", file.name, requestFile)
        } else null

        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

                //Firebase auth


                val token =  if(theFile == null){
                    UserRetrofitClient
                        .setInterceptor(authCode!!)
                        .getApiService()
                        .registerUser(userData)
                }
                else {
                    val firstName = userData.firstName.toRequestBody("text/plain".toMediaTypeOrNull())
                    val lastName = userData.lastName.toRequestBody("text/plain".toMediaTypeOrNull())
                    val middleName = userData.middleName.toRequestBody("text/plain".toMediaTypeOrNull())
                    val email = userData.email.toRequestBody("text/plain".toMediaTypeOrNull())
                    val level = userData.level.toRequestBody("text/plain".toMediaTypeOrNull())
                    val phoneNumber = userData.phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
                    val isApproved = userData.isApproved.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                    UserRetrofitClient
                        .setInterceptor(authCode!!)
                        .getApiService()
                        .registerUserWithPic(
                            file = theFile,
                            firstName = firstName,
                            lastName = lastName,
                            middleName = middleName,
                            email = email,
                            level = level,
                            phoneNumber = phoneNumber,
                            isApproved =  isApproved
                        )
                }

                if(token != null){
                    UserResponse(token)
                }
                else {
                    UserResponse(null, TokenError(0,
                        "Token is null"))
                }
            }
        } catch (exception: Exception) {
            UserResponse(null, TokenError(0,
                exception.message?:"exception"))
        }
    }

    suspend fun updateUser(file: File?, userData: UserDataModel): UserResponse  {

        val theFile = if(file != null) {
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", file.name, requestFile)
        } else null

        return try {
            withContext(Dispatchers.IO) {
                val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)
                val user =  if(theFile == null){
                    UserRetrofitClient
                        .setInterceptor(authCode!!)
                        .getApiService()
                        .updaterUser(userData)
                }
                else {
                    val firstName = userData.firstName.toRequestBody("text/plain".toMediaTypeOrNull())
                    val lastName = userData.lastName.toRequestBody("text/plain".toMediaTypeOrNull())
                    val middleName = userData.middleName.toRequestBody("text/plain".toMediaTypeOrNull())
                    val email = userData.email.toRequestBody("text/plain".toMediaTypeOrNull())
                    val level = userData.level.toRequestBody("text/plain".toMediaTypeOrNull())
                    val phoneNumber = userData.phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
                    val isApproved = userData.isApproved.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                    UserRetrofitClient
                        .setInterceptor(authCode!!)
                        .getApiService()
                        .updaterUserWithPic(
                            userData._id,
                            file = theFile,
                            firstName = firstName,
                            lastName = lastName,
                            middleName = middleName,
                            email = email,
                            level = level,
                            phoneNumber = phoneNumber,
                            isApproved =  isApproved
                        )
                }

                if(user != null){
                    UserResponse(user)
                }
                else {
                    UserResponse (null, TokenError(0,
                        "Token is null"))
                }
            }
        } catch (exception: Exception) {
            UserResponse(null, TokenError(0,
                exception.message?:"exception"))
        }
    }

    suspend fun getUser(key: String): UserResponse  {
        return try {
            val authCode = StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)

            withContext(Dispatchers.IO) {
                val user =  UserRetrofitClient
                    .setInterceptor(authCode?: Cache.authCode)
                    .getApiService()
                    .getUser(UserValues(key))

                if(user != null) {
                    UserResponse(user)
                }
                else {
                    UserResponse(null, TokenError(0, "User not found"))
                }
            }

        }catch (e: Exception) {
            UserResponse(null)
        }
    }
}