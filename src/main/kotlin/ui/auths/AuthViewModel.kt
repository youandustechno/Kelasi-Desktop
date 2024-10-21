package ui.auths

import models.AuthCodeResponse
import models.auth.*
import models.group.GroupApi
import ui.settings.SettingsViewModel
import java.io.File


class AuthViewModel: SettingsViewModel (){

    suspend fun verifyAuthCode(text: String): AuthCodeResponse {
        val groupApi = GroupApi()

        return groupApi.verifyTenant(text)
    }

    suspend fun startLoginPhone(phone: String): UserResponse {
        val authApi = UserAuthApi()
        val response = authApi.loginWithPhone(phoneNumber = phone)
        return if(response.token != null) {
            authApi.getUser(phone)
        } else {
            UserResponse(error = TokenError(error = "Login failure"))
        }
    }

    suspend fun login(phone: String) {
        val authApi = UserAuthApi()
        val response = authApi.loginWithPhone(phoneNumber = phone)

        return
    }

    suspend fun startLoginEmail(credentials: EmailAndPassComponent): UserResponse {
        val authApi = UserAuthApi()
        val response = authApi.loginWithEmail(credentials)
        return if(response.token != null) {
            authApi.getUser(credentials.email)
        } else {
            UserResponse(error = TokenError(error = "Login failure"))
        }
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
                UserResponse(error = TokenError(error = "Authentication failure"))
            }
        } else {
            UserResponse(error = TokenError(error = "Authentication failure"))
        }
    }
}