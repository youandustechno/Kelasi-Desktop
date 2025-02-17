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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.StorageHelper
import helpers.StorageHelper.Companion.AUTH_CODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.BaseValues
import models.BaseValues.TENANT_CODE
import ui.LocalizedStrings
import ui.LocalizedStrings.ENTER_EMAIL
import ui.LocalizedStrings.INSTITUTION
import ui.LocalizedStrings.NEXT
import ui.LocalizedStrings.VERIFY
import ui.NavHelper
import ui.NavKeys.EMPTY
import ui.Route
import ui.utilities.*
import ui.utilities.FieldsValidation.getDomain
import ui.utilities.FieldsValidation.isValid
import ui.utilities.FieldsValidation.isValidCode
import ui.utilities.FieldsValidation.isValidEmail


@Composable
fun OrgAuth(orgFound:(NavHelper) -> Unit) {

    val authViewModel = AuthViewModel()
    val coroutineScope = rememberCoroutineScope()
    var isFailure: Boolean? by remember { mutableStateOf(null) }
    var errorType by remember { mutableStateOf(ErrorState.None) }

    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        var text by remember { mutableStateOf(EMPTY) }
        val buttonText by remember { mutableStateOf(LocalizedStrings.get(NEXT)) }

        Column (Modifier
            .width(500.dp)
            .height(400.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp, 60.dp)
            ) {
                Text(LocalizedStrings.get(ENTER_EMAIL),
                    style = MaterialTheme.typography.caption.copy(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 24.sp
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column (Modifier
                .fillMaxWidth()
                .weight(1f, true),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Box(Modifier
                    .widthIn(300.dp, 400.dp)
                    .padding(start = 4.dp, end = 4.dp)) {

                    UserEmailFields(text) {
                        text = it
                    }
                }

                if(ErrorState.None != errorType) {
                    Spacer(Modifier.height(5.dp))
                    DisplayError(errorType)
                }
                Spacer(Modifier.height(20.dp))

                Box(Modifier
                    .widthIn(300.dp, 400.dp)
                    .padding(start = 4.dp, end = 4.dp)) {

                    LoginButton(buttonText) {
                        //Make http call
                        if(!text.isValidEmail()) {
                            errorType = ErrorState.Wrong_Code
                        } else {
                            coroutineScope.launch(Dispatchers.IO) {
                                if(text.isValid()) {
                                    val result =  authViewModel.verifyAuthCode(text.getDomain())
                                    delay(500L)
                                    withContext(Dispatchers.Main) {
                                        if(result.auth != null) {
                                            val lang = result.auth?.tenantLang
                                            result.auth?.let { tenant ->
                                                BaseValues.KEY = tenant.key
                                                BaseValues.PhoneRegex = tenant.regex
                                                BaseValues.PhoneSample = tenant.format
                                                BaseValues.LEVELS = tenant.levels
                                                //PreferenceHelper().saveAuthCode(it)
                                                TENANT_CODE = tenant.domain
                                                StorageHelper().saveInStorage(AUTH_CODE, tenant.domain)
                                            }
                                            LocalizedStrings.setLanguage(if(lang == "en") LocalizedStrings
                                                .LanguageOption.EN
                                            else LocalizedStrings.LanguageOption.FR)
                                            //prefs.put("group", result.org!!.tenantCode)

                                            orgFound.invoke(NavHelper(Route.Register))
                                        } else {
                                            errorType = when(result.error?.errorMessage?.lowercase())  {
                                                "timeout" -> ErrorState.IT_US
                                                else -> ErrorState.Auth_Failure
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                }
                Spacer(Modifier.height(30.dp))
            }
        }
    }
}