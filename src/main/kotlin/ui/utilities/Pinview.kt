package ui.utilities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun PinView(pinLength: Int = 8, onPinEntered: (String) -> Unit) {
    val pinValues = remember { mutableStateListOf(*Array(pinLength){""}) }
    val focusRequesters = remember { List(pinLength) { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for(index in 0 until pinLength) {
            OutlinedTextField(
                value = pinValues[index],
                onValueChange = { value ->
                    if(value.length <= 1) {
                        pinValues[index] = value

                        if(value.isNotEmpty() && index< pinLength -1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        if(pinValues.all {  it.isNotEmpty() }) {
                            onPinEntered(pinValues.joinToString(""))
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = MaterialTheme
                    .typography
                    .caption,
                modifier = Modifier
                    .width(55.dp)
                    .padding(horizontal = 4.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { keyEvent ->
                        if(keyEvent.type == KeyEventType.KeyDown) {
                            when(keyEvent.key) {
                                Key.Backspace, Key.Delete -> {
                                    if(pinValues[index].isEmpty() && index > 0) {
                                        focusRequesters[index - 1].requestFocus()
                                        pinValues[index - 1] = ""
                                    }
                                    true
                                }
                                Key.Tab -> {

                                    if(index < pinLength - 1) {
                                        focusRequesters[index + 1].requestFocus()
                                    } else {
                                        focusManager.clearFocus()
                                    }
                                    true
                                }
                                else -> false
                            }

                        } else {
                            false
                        }
                    },
            )
        }
    }
}