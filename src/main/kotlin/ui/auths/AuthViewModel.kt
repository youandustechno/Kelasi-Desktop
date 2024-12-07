package ui.auths

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import helpers.StorageHelper
import models.AuthCodeResponse
import models.BaseValues
import models.auth.*
import models.group.GroupApi
import ui.LocalizedStrings
import ui.LocalizedStrings.AUTHENTICATION_FAILURE
import ui.LocalizedStrings.LOGIN_FAILURE
import ui.LocalizedStrings.USERNAME
import ui.LocalizedStrings.VERIFICATION_CODE_KEY
import ui.NavKeys
import ui.settings.SettingsViewModel
import ui.utilities.Others.isTokenExpired
import ui.utilities.Others.timeStampToLocalDateTime
import java.io.File
import java.time.LocalDateTime
import java.util.*


class AuthViewModel: SettingsViewModel () {

    companion object {
        var currentToken: String? = null
    }

    suspend fun verifyAuthCode(text: String): AuthCodeResponse {
        val groupApi = GroupApi()

        return groupApi.verifyTenant(text)
    }

    suspend fun startLoginPhone(phone: String): TokenResponse {
        val authApi = UserAuthApi()
        val response = authApi.loginWithPhone(phoneNumber = phone)
        return if(response.token != null) {
            response
        } else {
            TokenResponse (error = TokenError(error = LocalizedStrings.get(LOGIN_FAILURE)))
        }
    }

    suspend fun startLoginEmail(credentials: EmailAndPassComponent): TokenResponse  {
        val authApi = UserAuthApi()
        val response = authApi.loginWithEmail(credentials)
        return if(response.token != null) {
            response
        } else {
            TokenResponse (error = TokenError(error = LocalizedStrings.get(LOGIN_FAILURE)))
        }
    }

    suspend fun recoverUser(credential: UserCredentials) : UserResponse {
        val authApi = UserAuthApi()
        return authApi.recoverUserCredentials(credential)
    }

    suspend fun getAuthenticatedUser(credential: String) : UserResponse {
        val authApi = UserAuthApi()
        return authApi.getUser(credential)
    }

    suspend fun startRegistration(user: UserDataModel): UserResponse {
        val userAuthApi = UserAuthApi()
        val response = userAuthApi
            .registerAccessCredentials(EmailAndPassComponent(user.email, user.password, user.phoneNumber))
        return if(response.token != null) {
           val loginResponse =  userAuthApi.loginWithPhone(user.phoneNumber)
            if(loginResponse.token != null) {
                val file = filePath?.let {path -> File(path)  }
                userAuthApi.registerUser(file, user)
            } else {
                UserResponse(error = TokenError(error = LocalizedStrings.get(AUTHENTICATION_FAILURE)))
            }
        } else {
            UserResponse(error = TokenError(error = LocalizedStrings.get(AUTHENTICATION_FAILURE)))
        }
    }

    fun isTokenStillValid(userCredential: UserDataModel?, userCode: String): Boolean  {

        return try {
            val secret = BaseValues.KEY
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm).build()
            val tokenCode = StorageHelper().retrieveFromStorage(StorageHelper.TOKEN_CODE)
            val decodeJWT = verifier.verify(currentToken?.trim()?:tokenCode?.trim())

            val username = decodeJWT.getClaim(NavKeys.USERNAME).asString()
            val verificationCode = decodeJWT.getClaim(NavKeys.VERIFICATION_CODE).asString()
            val exp = decodeJWT.expiresAt.time
            val date = timeStampToLocalDateTime(Date(exp).time)

            println("${LocalizedStrings.get(USERNAME)
                .replaceFirstChar { 
                    if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() }
            }: $username, ${LocalizedStrings.get(VERIFICATION_CODE_KEY)}: $verificationCode")


            if(isTokenExpired(currentTime = LocalDateTime.now(), tokenDate = date)) {
               currentToken = null
               false
            }
            else if(userCode != verificationCode) {
                currentToken = null
                false
            }
            else if(userCredential?.email == username && userCredential?.phoneNumber != username) {
                 true
             }
             else if(userCredential?.email != username && userCredential?.phoneNumber == username) {
                 true
             }
            else {
                 currentToken = null
                false
            }

        }catch (exc: Exception) {
            false
        }
    }
}