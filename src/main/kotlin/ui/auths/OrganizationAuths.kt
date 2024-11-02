package ui.auths

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import helpers.StorageHelper
import helpers.StorageHelper.Companion.AUTH_CODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ui.NavHelper
import ui.NavKeys.EMPTY
import ui.Route
import ui.utilities.ErrorCard
import ui.utilities.FieldsValidation.isValid
import ui.utilities.LoginButton
import ui.utilities.OrgaAuthText


@Composable
fun OrgAuth(orgFound:(NavHelper) -> Unit) {

    val authViewModel = AuthViewModel()
    val coroutineScope = rememberCoroutineScope()
    var isFailure: Boolean? by remember { mutableStateOf(null) }
    var failureMessage: String? by remember { mutableStateOf(null) }

    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        var text by remember { mutableStateOf(EMPTY) }
        var buttonText by remember { mutableStateOf("VERIFY") }

        Column (Modifier
            .width(350.dp)
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(5.dp)) {

            Box(Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)) {
                OrgaAuthText(text) {
                    text = it
                }
            }
            
            LoginButton(buttonText) {
                //Make http call
                coroutineScope.launch(Dispatchers.IO) {
                    if(text.isValid()) {
                      val result =  authViewModel.verifyAuthCode(text)
                        delay(500L)
                        withContext(Dispatchers.Main) {
                            if(result.org != null) {
                                //prefs.put("group", result.org!!.tenantCode)
                                result.org?.tenantCode?.let {
                                    //PreferenceHelper().saveAuthCode(it)
                                    StorageHelper().saveInStorage(AUTH_CODE, it)
                                }
                                orgFound.invoke(NavHelper(Route.AuthLogin))
                            } else {
                                isFailure = true
                                failureMessage = result.error?.errorMessage
                            }
                        }
                    }
                }
            }
        }

        if(isFailure == true) {
            Spacer(Modifier.height(30.dp))
            Column(Modifier.sizeIn(150.dp, 100.dp, 400.dp, 150.dp)){
                ErrorCard {
                    Text("$failureMessage",
                        style = MaterialTheme.typography
                            .button
                            .copy(color = Color.DarkGray))
                }
            }
        }
    }
}