package ui.auths

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.StorageHelper
import helpers.StorageHelper.Companion.AUTH_CODE
import helpers.StorageHelper.Companion.TOKEN_CODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.auth.EmailAndPassComponent
import ui.Cache.userCache
import ui.LocalizedStrings
import ui.LocalizedStrings.EMAIL
import ui.LocalizedStrings.LOGIN_HELP
import ui.LocalizedStrings.LOGIN_TITLE
import ui.LocalizedStrings.PHONE
import ui.LocalizedStrings.REGISTER
import ui.LocalizedStrings.VALIDATE
import ui.LocalizedStrings.VERIFICATION_CODE_TITLE
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

    var emailSave by remember { mutableStateOf(EMPTY) }
    var passwordSave by remember { mutableStateOf(EMPTY) }
    var phoneSave by remember { mutableStateOf(EMPTY) }

    var userCode by remember { mutableStateOf(EMPTY) }
    var loginButton by remember { mutableStateOf(LocalizedStrings.get(LOGIN_TITLE)) }
    var loginMode by remember { mutableStateOf(LocalizedStrings.get(PHONE)) }
    var isPhoneAuth by remember { mutableStateOf(false) }
    var isTokenValid by remember { mutableStateOf(false) }

    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Column (Modifier
            .width(500.dp)
            .height(400.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
                .padding(start = 4.dp, end = 4.dp)) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box (Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp)) {

                        val titleText = if (isTokenValid) {
                            LocalizedStrings.get(VERIFICATION_CODE_TITLE)
                        } else {
                            LocalizedStrings.get(LOGIN_TITLE)
                        }

                        Box(
                            Modifier
                                .wrapContentSize()
                                .align(Alignment.Center)
                        ) {
                            Text(titleText,
                                style = MaterialTheme.typography.caption.copy(
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Light,
                                    fontFamily = FontFamily.Serif,
                                    lineHeight = 24.sp
                                ))
                        }


                        Box(Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterEnd)) {

                            ResourceImageController30by30("image/icon_arrow_back.svg") {
                                //todo remove auth code and clear everything

                                if(isTokenValid) {
                                    loginButton = LocalizedStrings.get(LOGIN_TITLE)
                                    isTokenValid = false

                                } else {
                                    onClick.invoke(NavHelper(Route.AuthOrg))
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically) {
                        loginMode = if(isPhoneAuth)  LocalizedStrings.get(EMAIL) else LocalizedStrings.get(PHONE)
                        if(!isTokenValid) {
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
                    }
                    Spacer(Modifier.height(10.dp))
                    if(isPhoneAuth && !isTokenValid) {
                        Box(Modifier
                            .widthIn(300.dp, 400.dp)) {

                            UserPhoneFields(phone) {
                                phone = it
                            }
                        }

                    } else if(isTokenValid) {
                        loginButton = LocalizedStrings.get(VALIDATE)

                        PinView { pinCode ->
                            userCode = pinCode
                        }
                    }
                    else {
                        Column(Modifier
                            .widthIn(300.dp, 400.dp)
                            .wrapContentHeight()) {

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
            }
            Spacer(Modifier.height(20.dp))
            Column(Modifier
                .widthIn(300.dp, 400.dp)) {
                LoginButton(loginButton) {

                    if(loginButton.equals(LocalizedStrings.get(VALIDATE), true)) {

                        if(isTokenValid) {
                            coroutineScope.launch(Dispatchers.IO) {
                                val credential: String = email.ifEmpty {
                                    phone.ifEmpty {
                                        phoneSave.ifEmpty {
                                            emailSave
                                        }
                                    }
                                }

                                val result  = authViewModel
                                    .getAuthenticatedUser(credential)
                                delay(500L)
                                withContext(Dispatchers.Main) {
                                    if (result.user != null) {
                                        val map = mutableMapOf<String, Any>()
                                        val isVerified = authViewModel.isTokenStillValid(result.user, userCode)
                                        if(isVerified) {

                                            userCode = EMPTY
                                            passwordSave = EMPTY
                                            emailSave = EMPTY
                                            phoneSave = EMPTY
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
                                        StorageHelper().saveInStorage(TOKEN_CODE, result.token.token)
                                        passwordSave = password
                                        emailSave = email
                                        phoneSave = phone
                                        password = EMPTY
                                        email = EMPTY
                                        phone = EMPTY
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
            }
            Row(Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {

                Row(Modifier.wrapContentWidth()) {
                    LinkButton(LocalizedStrings.get(REGISTER), color = Color(0XFFc5aca0)) {
                        onClick.invoke(NavHelper(Route.Register))
                    }
                    Spacer(Modifier.width(8.dp))
                    LinkButton(LocalizedStrings.get(LOGIN_HELP)) {
                        onClick.invoke(NavHelper(Route.HelpLogin))
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}