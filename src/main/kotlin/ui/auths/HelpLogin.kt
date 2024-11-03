package ui.auths

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.auth.UserCredentials
import ui.Cache.userCache
import ui.NavHelper
import ui.NavKeys.EMPTY
import ui.Route
import ui.utilities.*

@Composable
fun HelpLogin(onClick: (NavHelper) -> Unit) {

    val authViewModel = AuthViewModel()
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf(EMPTY) }
    var phone by remember { mutableStateOf(EMPTY) }
    var loginButton by remember { mutableStateOf("VALIDATE") }
    var isUserFound by remember { mutableStateOf(false) }

    var firstname by remember { mutableStateOf(userCache?.firstName?:EMPTY) }
    var lastname by remember { mutableStateOf(userCache?.lastName?:EMPTY) }
    var middlename by remember { mutableStateOf(userCache?.middleName?:EMPTY) }

    Column(
        Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Column (
            Modifier
            .width(500.dp)
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
                .padding(start = 4.dp, end = 4.dp)) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box (Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp)){
                        Box(Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterEnd)) {

                            ResourceImageController30by30("image/icon_arrow_back.svg") {
                                onClick.invoke(NavHelper(Route.AuthLogin))
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    if(!isUserFound) {

                        FirstNameFields(firstname) {
                            firstname = it
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        LastNameFields(lastname) {
                            lastname = it
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        MiddleFields(middlename) {
                            middlename = it
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        UserPhoneFields(phone) {
                            phone = it
                        }
                    }
                    else {
                        Text("The email you use to login is: ")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(email)
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            LoginButton(loginButton) {
                if(loginButton.equals("VALIDATE", true)) {

                    coroutineScope.launch(Dispatchers.IO) {
                        val result  = authViewModel
                            .recoverUser(
                                UserCredentials(
                                    firstName = firstname,
                                    lastName = lastname,
                                    middleName = middlename,
                                    phoneNumber = phone
                                )
                            )

                        delay(500L)
                        withContext(Dispatchers.Main) {
                            if (result.user != null) {
                                email = result.user.email
                                isUserFound = true
                            } else {
                                //Todo show error for authentication failure
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}