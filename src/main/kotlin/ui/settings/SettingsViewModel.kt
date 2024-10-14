package ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.auth.UserAuthApi
import models.auth.UserDataModel
import models.auth.UserResponse
import java.io.File

class SettingsViewModel {

    var filePath by mutableStateOf<String?>(null)
        private set

    fun selectImageFile(path: String) {
        filePath = path
    }

    suspend fun startRegistration(user: UserDataModel, isRegister: Boolean): UserResponse {
        val userAuthApi = UserAuthApi()
        val file = filePath?.let {path -> File(path)  }


        return if(true) {
            userAuthApi.registerUser(file, user)
        }
        else {
            userAuthApi.updateUser(file, user)
        }
    }

}