package ui

import models.auth.UserAuthApi
import ui.NavKeys.EMPTY
import ui.utilities.LoginUtils.UID

class GlobalViewModel {

    suspend fun startLogout() : Boolean {
        val userAuthApi = UserAuthApi()
        val token = EMPTY
        return userAuthApi.logout(token, UID)
    }
}