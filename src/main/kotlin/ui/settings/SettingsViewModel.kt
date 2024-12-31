package ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.auth.*
import ui.LocalizedStrings
import ui.LocalizedStrings.AUTHENTICATION_FAILURE
import ui.LocalizedStrings.FAIL_TO_UPDATE
import java.io.File

open class SettingsViewModel {

    var filePath by mutableStateOf<String?>(null)
        private set

    fun selectImageFile(path: String) {
        filePath = path
    }

    suspend fun updateUserInfo(user: UserDataModel, uid: String): UserResponse {
        val userAuthApi = UserAuthApi()
        val file = filePath?.let {path -> File(path)  }

       val credentials = userAuthApi
           .changeUserPassword(ChangePasswordModel(user.password, uid))

        return if(credentials.user?.password != null) {
            userAuthApi.updateUser(file, user)
        }else {
            UserResponse(null, TokenError(code = 0, error = LocalizedStrings.get(FAIL_TO_UPDATE)))
        }
    }
}
//suspend fun startRegistration(user: UserDataModel): UserResponse {
//    val userAuthApi = UserAuthApi()
//    val response = userAuthApi
//        .registerAccessCredentials(EmailAndPassComponent(user.email, user.password, user.phoneNumber))
//    return if(response.token != null) {
//        val loginResponse =  userAuthApi.loginWithPhone(user.phoneNumber)
//        if(loginResponse.token != null) {
//            val file = filePath?.let {path -> File(path)  }
//            userAuthApi.registerUser(file, user)
//        } else {
//            UserResponse(error = TokenError(error = LocalizedStrings.get(AUTHENTICATION_FAILURE)))
//        }
//    } else {
//        UserResponse(error = TokenError(error = LocalizedStrings.get(AUTHENTICATION_FAILURE)))
//    }
//}