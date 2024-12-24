package ui.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.BaseValues
import ui.LocalizedStrings
import ui.LocalizedStrings.ADD_DURATION
import ui.LocalizedStrings.ANSWER
import ui.LocalizedStrings.ASSERTION
import ui.LocalizedStrings.CONFIRM_NEW_PASSWORD
import ui.LocalizedStrings.CONFIRM_PASSWORD
import ui.LocalizedStrings.COURSE_DESCRIPTION
import ui.LocalizedStrings.COURSE_NAME
import ui.LocalizedStrings.EMAIL
import ui.LocalizedStrings.EMAIL_SAMPLE
import ui.LocalizedStrings.FIRST_NAME
import ui.LocalizedStrings.INSTRUCTOR
import ui.LocalizedStrings.LAST_NAME
import ui.LocalizedStrings.LEVEL
import ui.LocalizedStrings.MIDDLE_NAME
import ui.LocalizedStrings.MODULE_NAME
import ui.LocalizedStrings.NEW_PASSWORD
import ui.LocalizedStrings.PASSWORD
import ui.LocalizedStrings.PHONE
import ui.LocalizedStrings.PHONE_NUMBER
import ui.LocalizedStrings.QUESTION
import ui.LocalizedStrings.VIDEO_DESCRIPTION
import ui.LocalizedStrings.VIDEO_TITLE
import ui.NavKeys.EMPTY

//Outlined text field

//Text fields
@Composable
fun PhoneText(phoneNumber: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(phoneNumber)) }
    AuthTextFieldWithIcons(text.text, Icons.Default.Phone, LocalizedStrings.get(PHONE), onChange)
}

@Composable
fun OrgaAuthText(phoneNumber: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(phoneNumber)) }
    AuthTextFieldWithIcons(text.text, Icons.Default.Edit, "Code", onChange)
}

@Composable
fun AnswerFields(question: String, onChange: (String) -> Unit) {
    Spacer(Modifier.height(20.dp))
    DataQuestionFields(question, LocalizedStrings.get(ANSWER), onChange)
}


@Composable
fun AnswerField(value: String, onChange: (String) -> Unit) {
    Spacer(Modifier.height(20.dp))
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        TextField(value,
            onValueChange = { onChange(it)},
            modifier = Modifier
                .defaultMinSize(minHeight = 100.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Transparent,
                disabledTextColor = Color.Black,
                disabledIndicatorColor = Color.Transparent,
                errorLeadingIconColor = MaterialTheme.colors.error,
                errorCursorColor = MaterialTheme.colors.error
            ),
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 1.4.sp,
                fontWeight = FontWeight.Light,
                lineHeight = 28.sp),
        )

    }
}

@Composable
fun InstructorFields(instructor: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(instructor)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, LocalizedStrings.get(INSTRUCTOR), onChange)
}

@Composable
fun CourseFields(courseName: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(courseName)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, LocalizedStrings.get(COURSE_NAME) , onChange)
}

@Composable
fun CourseDescriptionFields(courseDescription: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(courseDescription)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, LocalizedStrings.get(COURSE_DESCRIPTION), onChange)
}

@Composable
fun ModuleFields(module: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(module)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, LocalizedStrings.get(MODULE_NAME), onChange)
}

@Composable
fun VideoTitleFields(title: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(title)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, LocalizedStrings.get(VIDEO_TITLE), onChange)
}

@Composable
fun VideoDescriptionFields(title: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(title)) }
    //DataValueFields(text.text, "Video Description", onChange)
    TextFieldWithIcons(text.text, Icons.Default.Edit, LocalizedStrings.get(VIDEO_DESCRIPTION), onChange)
}

@Composable
fun TimeFields(question: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(question)) }
    Spacer(Modifier.height(5.dp))

    TimeDataValueFields(text.text, LocalizedStrings.get(ADD_DURATION), onChange)
}

@Composable
fun QuestionFields(question: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(question)) }
    Spacer(Modifier.height(20.dp))
    DataQuestionFields(text.text, LocalizedStrings.get(QUESTION), onChange)
}

@Composable
fun AssertionFields(question: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(question)) }
    Spacer(Modifier.height(5.dp))

    DataValueFields(text.text, LocalizedStrings.get(ASSERTION), onChange)
}

@Composable
fun UserPasswordFields(userName: String, isError: Boolean = false, onChange: (String) -> Unit) {
    PasswordTextFieldWithIcon(userName, Icons.Default.Lock, LocalizedStrings.get(PASSWORD), isError, onChange)
}

@Composable
fun UserNewPasswordFields(userName: String, isError: Boolean = false, onChange: (String) -> Unit) {
    PasswordTextFieldWithIcon(userName, Icons.Default.Lock, LocalizedStrings.get(NEW_PASSWORD), isError, onChange)
}

@Composable
fun UserConfirmPasswordFields(userName: String, isRegistration: Boolean = false, isError: Boolean, onChange: (String) -> Unit) {
    PasswordTextFieldWithIcon(userName, Icons.Default.Lock,
        if(!isRegistration)LocalizedStrings.get(CONFIRM_NEW_PASSWORD)
        else LocalizedStrings.get(CONFIRM_PASSWORD), isError, onChange)
}

@Composable
fun UserEmailFields(userName: String, isError: Boolean = false, onChange: (String) -> Unit) {
    GenericTextFieldWithIcon(userName, Icons.Default.Email, LocalizedStrings.get(EMAIL), isError, onChange)
}

@Composable
fun UserPhoneFields(userName: String, isError: Boolean = false, onChange: (String) -> Unit) {
    GenericTextFieldWithIcon(userName, Icons.Default.Phone, LocalizedStrings.get(PHONE_NUMBER), isError, onChange)
}

@Composable
fun MiddleFields(userName: String, isError: Boolean = false, onChange: (String) -> Unit) {
    GenericTextFieldWithIcon(userName, Icons.Default.Person, LocalizedStrings.get(MIDDLE_NAME), isError, onChange)
}

@Composable
fun FirstNameFields(userName: String, isError: Boolean = false, onChange: (String) -> Unit) {
    GenericTextFieldWithIcon(userName, Icons.Default.Person, LocalizedStrings.get(FIRST_NAME), isError,onChange)
}

@Composable
fun LastNameFields(userName: String, isError: Boolean = false, onChange: (String) -> Unit) {
    GenericTextFieldWithIcon(userName, Icons.Default.Person, LocalizedStrings.get(LAST_NAME), isError, onChange)
}

@Composable
fun LevelFields(userName: String, isError: Boolean = false, onChange: (String) -> Unit) {
    GenericTextFieldWithIcon(userName, Icons.Default.Edit, LocalizedStrings.get(LEVEL), isError,  onChange)
}


@Composable
private fun DataQuestionFields(value: String, label: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }
    Column(Modifier.fillMaxWidth()
        .wrapContentHeight()) {
        TextField(
            value = text,
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                text = it
                onChange(it)
            },
            //textColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, disabledTextColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, backgroundColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, cursorColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, errorCursorColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, focusedIndicatorColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, unfocusedIndicatorColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, disabledIndicatorColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, errorIndicatorColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, leadingIconColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, disabledLeadingIconColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, errorLeadingIconColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, trailingIconColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, disabledTrailingIconColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, errorTrailingIconColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, focusedLabelColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, unfocusedLabelColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, disabledLabelColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, errorLabelColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, placeholderColor: androidx.compose.ui.graphics.Color = COMPILED_CODE, disabledPlaceholderColor: androidx.compose.ui.graphics.Color = COMPILED_CODE): androidx.compose.material.TextFieldColors { /* compiled code */ }
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = MaterialTheme.colors.primaryVariant,
                focusedLabelColor = Color.Magenta,
                disabledTextColor = MaterialTheme.colors.background,
                disabledIndicatorColor = MaterialTheme.colors.primarySurface,
                errorLeadingIconColor = MaterialTheme.colors.error,
                errorCursorColor = MaterialTheme.colors.error
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}


@Composable
private fun TimeDataValueFields(value: String, label: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }
    Column(Modifier.fillMaxWidth()
        .wrapContentHeight()) {
        TextField(
            value = text,
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                text = it
                onChange.invoke(it)
            }
        )
    }
}


@Composable
private fun DataValueFields(value: String, label: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }
    Column(Modifier.fillMaxWidth()
        .wrapContentHeight()) {
        TextField(
            value = text,
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                text = it
                onChange.invoke(it)
            }
        )
    }
}


@Composable
fun TextFieldWithIcons(value: String, icon: ImageVector, description: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }
    val isPhoneNumber by remember { mutableStateOf(description.equals(LocalizedStrings.get(PHONE_NUMBER), true)) }

    Column(Modifier.wrapContentWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = text,
            leadingIcon = { Icon(imageVector = icon, contentDescription = description) },
            //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = if(isPhoneNumber)KeyboardType.Phone
            else KeyboardType.Text),
            onValueChange = {
                text = it
                onChange.invoke(it)
            },
            modifier = Modifier
                .wrapContentWidth(),
            //.background(color = Color.White),
            label = { Text(text = description) },
            placeholder = { Text(getPlaceHolderValue(description),
                fontSize = 12.sp,
                color = Color.LightGray) },
        )
    }
}

@Composable
fun AuthTextFieldWithIcons(value: String, icon: ImageVector, description: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }
    val isPhoneNumber by remember { mutableStateOf(description.equals(LocalizedStrings.get(PHONE_NUMBER), true)) }

    Column(Modifier.fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = text,
            leadingIcon = { Icon(imageVector = icon, contentDescription = description) },
            //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = if(isPhoneNumber) KeyboardType.Phone
            else KeyboardType.Text),
            onValueChange = {
                text = it
                onChange.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth(),
            //.background(color = Color.White),
            label = { Text(text = description) },
            placeholder = { Text(text = getPlaceHolderValue(description),
                color = Color.LightGray,
                fontSize = 12.sp) },
        )
    }
}

@Composable
fun  GenericTextFieldWithIcon(value: String, icon: ImageVector, description: String, isError: Boolean = false, onChange: (String) -> Unit) {

    val isPhoneNumber by remember { mutableStateOf(description.equals(LocalizedStrings.get(PHONE_NUMBER), true)) }
    var oldTextValue by remember { mutableStateOf(EMPTY) }

    if(isPhoneNumber) {
        value.trim()
        if(value.trim().contains(Regex("a-zA-Z"))) {
            EMPTY
        }
        else {
            oldTextValue = value.trim()
        }
    }
    else {
        oldTextValue = value
    }

    Column(Modifier.fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = oldTextValue,
            leadingIcon = { Icon(imageVector = icon, contentDescription = description) },
            //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = if(isPhoneNumber) {
                KeyboardType.Number
            }
            else KeyboardType.Text),
            onValueChange = {
                onChange.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(60.dp),
            isError = isError,
            //.background(color = Color.White),
            label = { Text(text = description, style = TextStyle(fontSize = 10.sp)) },
            placeholder = {
                Text(text = getPlaceHolderValue(description),
                    color = Color.LightGray,
                    fontSize = 12.sp)
                          },
            textStyle = TextStyle(fontSize = 12.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White
            )
        )
    }
}

@Composable
fun  PasswordTextFieldWithIcon(
               value: String,
               icon: ImageVector,
               description: String,
               isError: Boolean = false,
               onChange: (String) -> Unit) {

    val isPhoneNumber by remember { mutableStateOf(description.equals(LocalizedStrings.get(PHONE_NUMBER), true)) }
    var isHidden by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = value,
            leadingIcon = {
                Icon(imageVector = icon, contentDescription = description)
                 },
            trailingIcon = {
                Box(Modifier.wrapContentSize()
                    .clickable {
                        isHidden = !isHidden
                    }) {
                    if(isHidden) {
                        //painterResource(res)
                        Icon(painter = painterResource("image/icon_visible_not.svg"),
                            contentDescription = "close eye",
                            modifier = Modifier.size(25.dp))
                    }
                    else {
                        Icon(painter = painterResource("image/icon_visibility.svg"),
                            contentDescription = "watch",
                            modifier = Modifier.size(25.dp))
                    }
                } },
            keyboardOptions = KeyboardOptions(keyboardType = if(isPhoneNumber) {
                KeyboardType.Number
            }
            else KeyboardType.Text),
            onValueChange = {
                if(isHidden) {
                    onChange.invoke(it)
                } else {
                    onChange.invoke(it)
                }
            },
            visualTransformation = if (isHidden) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(60.dp),
            isError = isError,
            //.background(color = Color.White),
            label = { Text(text = description, style = TextStyle(fontSize = 10.sp)) },
            placeholder = {
                Text(text = getPlaceHolderValue(description),
                    color = Color.LightGray,
                    fontSize = 12.sp)
            },
            textStyle = TextStyle(fontSize = 12.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White
            )
        )
    }
}

private fun getPlaceHolderValue(description: String) : String {
    return when (description.lowercase()) {
        LocalizedStrings.get(PHONE_NUMBER).lowercase() -> BaseValues.PhoneSample
        LocalizedStrings.get(EMAIL).lowercase() -> LocalizedStrings.get(EMAIL_SAMPLE)
        else -> description
    }
}