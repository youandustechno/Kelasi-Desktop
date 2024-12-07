package ui.utilities

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.LocalizedStrings
import ui.LocalizedStrings.ADD_DURATION
import ui.LocalizedStrings.ANSWER
import ui.LocalizedStrings.ASSERTION
import ui.LocalizedStrings.CONFIRM_NEW_PASSWORD
import ui.LocalizedStrings.CONFIRM_PASSWORD
import ui.LocalizedStrings.COURSE_DESCRIPTION
import ui.LocalizedStrings.COURSE_NAME
import ui.LocalizedStrings.EMAIL
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

//Outlined text field

//Text fields
@Composable
fun PhoneText(phoneNumber: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(phoneNumber)) }
    AuthTextFieldWithIcons(text.text, Icons.Default.Phone, "Phone", onChange)
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
fun UserPasswordFields(userName: String, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Lock, LocalizedStrings.get(PASSWORD), onChange)
}

@Composable
fun UserNewPasswordFields(userName: String, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Lock, LocalizedStrings.get(NEW_PASSWORD), onChange)
}

@Composable
fun UserConfirmPasswordFields(userName: String, isRegistration: Boolean = false, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Lock,
        if(!isRegistration)LocalizedStrings.get(CONFIRM_NEW_PASSWORD)
        else LocalizedStrings.get(CONFIRM_PASSWORD), onChange)
}

@Composable
fun UserEmailFields(userName: String, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Email, LocalizedStrings.get(EMAIL), onChange)
}

@Composable
fun UserPhoneFields(userName: String, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Phone, LocalizedStrings.get(PHONE_NUMBER), onChange)
}

@Composable
fun MiddleFields(userName: String, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Person, LocalizedStrings.get(MIDDLE_NAME), onChange)
}

@Composable
fun FirstNameFields(userName: String, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Person, LocalizedStrings.get(FIRST_NAME), onChange)
}

@Composable
fun LastNameFields(userName: String, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Person, LocalizedStrings.get(LAST_NAME), onChange)
}

@Composable
fun LevelFields(userName: String, onChange: (String) -> Unit) {
    PaymentTextFieldWithIcon(userName, Icons.Default.Edit, LocalizedStrings.get(LEVEL), onChange)
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

    Column(Modifier.wrapContentWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = text,
            leadingIcon = { Icon(imageVector = icon, contentDescription = description) },
            //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = if(description.equals(LocalizedStrings.get(PHONE), true))KeyboardType.Number
            else KeyboardType.Text),
            onValueChange = {
                text = it
                onChange.invoke(it)
            },
            modifier = Modifier
                .wrapContentWidth(),
            //.background(color = Color.White),
            label = { Text(text = description) },
            // placeholder = { Text(text = description) },
        )
    }
}

@Composable
fun AuthTextFieldWithIcons(value: String, icon: ImageVector, description: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }

    Column(Modifier.fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = text,
            leadingIcon = { Icon(imageVector = icon, contentDescription = description) },
            //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = if(description.equals(LocalizedStrings.get(PHONE), true))KeyboardType.Number
            else KeyboardType.Text),
            onValueChange = {
                text = it
                onChange.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth(),
            //.background(color = Color.White),
            label = { Text(text = description) },
            // placeholder = { Text(text = description) },
        )
    }
}

@Composable
fun PaymentTextFieldWithIcon(value: String, icon: ImageVector, description: String, onChange: (String) -> Unit) {

    Column(Modifier.fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = value,
            leadingIcon = { Icon(imageVector = icon, contentDescription = description) },
            //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = if(description.equals(LocalizedStrings.get(PHONE), true)) {
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
            //.background(color = Color.White),
            label = { Text(text = description, style = TextStyle(fontSize = 10.sp)) },
            // placeholder = { Text(text = description) },
            textStyle = TextStyle(fontSize = 12.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White)
        )
    }
}