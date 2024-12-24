package ui.utilities

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.BaseValues
import ui.LocalizedStrings
import ui.LocalizedStrings.EMAIL_ERROR
import ui.LocalizedStrings.PASSWORD_ERROR
import ui.LocalizedStrings.PHONE_ERROR
import ui.LocalizedStrings.SORRY_FOR_INCONVENIENCE
import ui.LocalizedStrings.WRONG_CODE

@Composable
fun DisplayError(errorState: ErrorState) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {

        Column() {
            Spacer(Modifier.height(10.dp))

            val message =  when {
                errorState == ErrorState.Password -> {
                    LocalizedStrings.get(PASSWORD_ERROR)
                }
                errorState == ErrorState.ConfirmPassword -> {
                    LocalizedStrings.get(PASSWORD_ERROR)
                }
                errorState == ErrorState.Phone -> {
                    val map = mutableMapOf<String, String>()
                    map["pattern"] = BaseValues.PhoneSample
                    LocalizedStrings.get(PHONE_ERROR, map)
                }
                errorState == ErrorState.Email -> {
                    LocalizedStrings.get(EMAIL_ERROR)
                }
                errorState == ErrorState.IT_US -> {
                    LocalizedStrings.get(SORRY_FOR_INCONVENIENCE)
                }
                errorState == ErrorState.Wrong_Code -> {
                    LocalizedStrings.get(WRONG_CODE)
                }
                else -> errorState.name + "Error"
            }

            Box(
                Modifier
                .widthIn(300.dp, 380.dp)
                .wrapContentHeight()){
                Text(message,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography
                        .caption.copy(
                            fontSize = 12.sp,
                        )
                )
            }
        }
    }
}

enum class ErrorState {
    Password,
    ConfirmPassword,
    Email,
    Phone,
    Auth_Failure,
    Wrong_Code,
    IT_US,
    Bad_Entry,
    Login_Failure,
    None,
    VerificationError
}