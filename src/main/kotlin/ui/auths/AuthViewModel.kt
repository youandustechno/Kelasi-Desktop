package ui.auths

import models.AuthCodeResponse
import models.auth.TokenResponse
import models.auth.UserAuthApi
import models.group.GroupApi


class AuthViewModel {

    suspend fun verifyAuthCode(text: String): AuthCodeResponse {
        val groupApi = GroupApi()

        return groupApi.verifyTenant(text)
    }

    suspend fun authenticateUser(phone: String): TokenResponse {
        val authApi = UserAuthApi()
        return authApi.loginWithPhone(phoneNumber = phone)
    }
}