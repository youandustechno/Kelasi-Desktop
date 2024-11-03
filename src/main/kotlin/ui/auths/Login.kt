package ui.auths

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.auth.EmailAndPassComponent
import ui.Cache.userCache
import ui.NavHelper
import ui.NavKeys.EMPTY
import ui.NavKeys.USER_KEY
import ui.Route
import ui.utilities.*
import ui.utilities.FieldsValidation.isValid


@Composable
fun Login(onClick: (NavHelper) -> Unit) {

    val authViewModel = AuthViewModel()
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf(EMPTY) }
    var password by remember { mutableStateOf(EMPTY) }
    var phone by remember { mutableStateOf(EMPTY) }
    var userCode by remember { mutableStateOf(EMPTY) }
    var loginButton by remember { mutableStateOf("LOGIN") }
    var loginMode by remember { mutableStateOf("Phone") }
    var isPhoneAuth by remember { mutableStateOf(false) }
    var isTokenValid by remember { mutableStateOf(false) }

    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Column (Modifier
            .width(500.dp)
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
                .padding(start = 4.dp, end = 4.dp)) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically) {
                        loginMode = if(isPhoneAuth)  "Email" else "Phone"
                        Text(loginMode, style = MaterialTheme.typography.caption,
                            color = Color(0XFF3b1b49))
                        Spacer(Modifier.width(8.dp))
                        Switch(
                            isPhoneAuth,
                            onCheckedChange = { isPhoneAuth = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0XFF4A3125),
                                uncheckedThumbColor = Color(0XFF4A3125),
                                checkedTrackColor = Color(0XFF4A3125),
                                uncheckedTrackColor = Color(0XFFc5aca0)
                            )
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    if(isPhoneAuth && !isTokenValid) {
                        UserPhoneFields(phone) {
                            phone = it
                        }

                    } else if(isTokenValid) {
                        loginButton = "VALIDATE"

                        PinView { pinCode ->
                            userCode = pinCode
                            println("Entered PIN: $pinCode")
                        }
                    }
                    else {
                        UserEmailFields(email) {
                            email = it
                        }
                        Spacer(Modifier.height(10.dp))
                        UserPasswordFields(password) {
                            password = it
                        }
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            LoginButton(loginButton) {

                if(loginButton.equals("VALIDATE", true)) {

                    if(isTokenValid) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val result  = authViewModel.getAuthenticatedUser(email.ifEmpty { phone })
                            delay(500L)
                            withContext(Dispatchers.Main) {
                                if (result.user != null) {
                                    val map = mutableMapOf<String, Any>()
                                    val isVerified = authViewModel.isTokenStillValid(result.user, userCode)
                                    if(isVerified) {

                                        userCode = EMPTY
                                        password = EMPTY
                                        email = EMPTY
                                        phone = EMPTY
                                        userCache = result.user
                                        map[USER_KEY] = result.user
                                        //Todo persist
                                        onClick.invoke(NavHelper(Route.Dashboard, map))
                                    } else {
                                        //Todo show error for authentication failure
                                    }
                                } else {
                                    //Todo show error for authentication failure
                                }
                            }
                        }
                    }
                } else {

                    coroutineScope.launch(Dispatchers.IO) {

                        if(phone.isValid() && email.isValid() && password.isValid()) {
                            //TODO show error and say you can not login with email and phone credentials the same time.
                        }
                        else if(phone.isValid() && !email.isValid() && password.isValid()) {
                            //TODO show error and say you can not login with email and phone credentials the same time.
                        }
                        else if(!phone.isValid() && !email.isValid()) {
                            //TODO show error and say you can not login with email and phone credentials the same time.
                        }
                        else {
                            val result = if(email.isValid() && password.isValid()) {
                                authViewModel.startLoginEmail(EmailAndPassComponent(email, password))
                            } else {
                                authViewModel.startLoginPhone(phone)
                            }
                            delay(500L)
                            withContext(Dispatchers.Main) {
                                if (result.token != null) {
                                    AuthViewModel.currentToken = result.token.token
                                    isTokenValid = true
                                } else {
                                    //Todo show error for authentication failure
                                }
                            }
                        }
                    }
                }

            }

            Spacer(Modifier.height(20.dp))
            Row(Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {

                Row(Modifier.wrapContentWidth()) {
                    LinkButton("Register", color = Color(0XFFc5aca0)) {
                        onClick.invoke(NavHelper(Route.Register))
                    }
                    Spacer(Modifier.width(8.dp))
                    LinkButton("Help Login") {
                        onClick.invoke(NavHelper(Route.HelpLogin))
                    }
                }
            }
            Spacer(Modifier.height(20.dp))

//            LoginButton(registerLink) {
//                onClick.invoke(NavHelper(Route.Register))
//            }
        }
    }
}