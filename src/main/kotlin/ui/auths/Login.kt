package ui.auths

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.StorageHelper
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
import ui.utilities.FieldsValidation.isValidEmail
import ui.utilities.FieldsValidation.isValidPassword
import ui.utilities.FieldsValidation.isValidPhone


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
    var errorType by remember { mutableStateOf(ErrorState.None) }

    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Column (Modifier
            .width(500.dp)
            .heightIn(400.dp, 700.dp)
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
                            //switch phone or email auth type
                            Text(loginMode, style = MaterialTheme.typography.caption,
                                color = Color(0XFF3b1b49))
                            Spacer(Modifier.width(8.dp))
                            Switch(
                                isPhoneAuth,
                                onCheckedChange = {
                                    isPhoneAuth = it
                                    errorType = ErrorState.None
                                                  },
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
                        //Phone
                        Box(Modifier
                            .widthIn(300.dp, 400.dp)) {

                            UserPhoneFields(phone) {
                                phone = it
                                errorType = ErrorState.None
                            }
                        }

                    } else if(isTokenValid) {
                        //PinView
                        loginButton = LocalizedStrings.get(VALIDATE)

                        PinView { pinCode ->
                            userCode = pinCode
                            errorType = ErrorState.None
                        }
                    }
                    else {
                        //Email and Password
                        Column(Modifier
                            .widthIn(300.dp, 400.dp)
                            .wrapContentHeight()) {

                            UserEmailFields(email) {
                                email = it
                                errorType = ErrorState.None
                            }
                            Spacer(Modifier.height(10.dp))
                            UserPasswordFields(password) {
                                password = it
                                errorType = ErrorState.None
                            }
                        }
                    }

                    if(ErrorState.None != errorType) {
                        Spacer(Modifier.height(5.dp))
                        DisplayError(errorType)
                    }
                }
            }
            //Button
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

                                            errorType = ErrorState.Auth_Failure
                                        }
                                    } else {

                                        errorType = ErrorState.Auth_Failure
                                    }
                                }
                            }
                        }
                    } else {

                        coroutineScope.launch(Dispatchers.IO) {

                            if(phone.isValidPhone() && email.isValidEmail() && password.isValidPassword()) {
                                 errorType = ErrorState.Bad_Entry
                            }
                            else if(!password.isValidPassword() && !isPhoneAuth) {
                                errorType = ErrorState.Password
                            }
                            else if(!phone.isValidPhone() && isPhoneAuth) {
                                errorType = ErrorState.Phone
                            }
                            else if(!email.isValidEmail() && !isPhoneAuth) {
                                errorType = ErrorState.Email
                            }
                            else {
                                val result = if(email.isValidEmail() && password.isValidPassword()) {
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
                                        errorType = ErrorState.Login_Failure
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


