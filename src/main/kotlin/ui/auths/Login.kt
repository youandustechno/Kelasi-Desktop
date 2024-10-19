package ui.auths

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ui.utilities.FieldsValidation.isValid
import ui.utilities.LoginButton
import ui.utilities.PhoneText


@Composable
fun Login(loggedin: (Boolean) -> Unit) {

    val authViewModel = AuthViewModel()
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        var text by remember { mutableStateOf("") }
        var buttonText by remember { mutableStateOf("Login") }

        Column (Modifier
            .width(350.dp)
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(5.dp)) {
            Box(Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)) {

                PhoneText(text) {
                    text = it
                }
            }

            LoginButton(buttonText) {

                coroutineScope.launch(Dispatchers.IO) {
                    if(text.isValid()) {
                        val result =  authViewModel.authenticateUser(text)
                        withContext(Dispatchers.Main) {
                            if(result.token != null) {
                                //prefs.put("group", result.org!!.tenantCode)
                                //orgFound.invoke(NavHelper(Route.AuthLogin))
                                loggedin.invoke(true)
                            }
                        }
                    }
                }

            }
        }
    }
}