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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.auth.EmailAndPassComponent
import ui.NavHelper
import ui.Route
import ui.utilities.FieldsValidation.isValid
import ui.utilities.LoginButton
import ui.utilities.PhoneText
import ui.utilities.UserEmailFields
import ui.utilities.UserPasswordFields


@Composable
fun Login(onClick: (NavHelper) -> Unit) {

    val authViewModel = AuthViewModel()
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val loginButton by remember { mutableStateOf("LOGIN") }
    val registerLink by remember { mutableStateOf("REGISTER") }

    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Column (Modifier
            .width(350.dp)
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    PhoneText(phone) {
                        phone = it
                    }

                    Spacer(Modifier.height(10.dp))
                    Text("OR")
                    Spacer(Modifier.height(10.dp))

                    UserEmailFields(email) {
                        email = it
                    }
                    Spacer(Modifier.height(10.dp))
                    UserPasswordFields(password) {
                        password = it
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            LoginButton(loginButton) {

                coroutineScope.launch(Dispatchers.IO) {

                    if(!phone.isValid()) {
                        //TODO show error
                    } else if(!email.isValid()) {
                        //TODO show error
                    } else if(phone.isValid() && email.isValid() && password.isValid()) {
                        //TODO show error and say you can not login with email and phone credentials the same time.
                    }
                    else {
                        val result = if(email.isValid() && password.isValid()) {
                            authViewModel.startLoginEmail(EmailAndPassComponent(email, password))
                        } else {
                            authViewModel.startLoginPhone(phone)
                        }
                        email = ""
                        password = ""
                        withContext(Dispatchers.Main) {
                            if (result.user != null) {
                                val map = mutableMapOf<String, Any>()
                                map["user"] = result.user
                                //Todo persist
                                onClick.invoke(NavHelper(Route.Dashboard, map))
                            } else {
                                //Todo show error for authentication failure
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Text("DON'T HAVE CREDENTIALS?")
            Spacer(Modifier.height(10.dp))

            LoginButton(registerLink) {
                onClick.invoke(NavHelper(Route.Register))
            }
        }
    }
}