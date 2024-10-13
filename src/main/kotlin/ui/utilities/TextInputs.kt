package ui.utilities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

//Outlined text field

//Text fields
@Composable
fun PhoneText(phoneNumber: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(phoneNumber)) }
    TextFieldWithIcons(text.text, Icons.Default.Phone, "Phone", onChange)
}

@Composable
fun OrgaAuthText(phoneNumber: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(phoneNumber)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, "Auth Code", onChange)
}

@Composable
fun AnswerFields(question: String, onChange: (String) -> Unit) {
    Spacer(Modifier.height(20.dp))
    DataQuestionFields(question, "Answer", onChange)
}

@Composable
fun InstructorFields(instructor: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(instructor)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, "Instructor Name", onChange)
}

@Composable
fun CourseFields(courseName: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(courseName)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, "Course Name", onChange)
}

@Composable
fun CourseDescriptionFields(courseDescription: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(courseDescription)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, "Course Description", onChange)
}

@Composable
fun ModuleFields(module: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(module)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, "Module Name", onChange)
}

@Composable
fun VideoTitleFields(title: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(title)) }
    TextFieldWithIcons(text.text, Icons.Default.Edit, "Video Title", onChange)
}

@Composable
fun VideoDescriptionFields(title: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(title)) }
    //DataValueFields(text.text, "Video Description", onChange)
    TextFieldWithIcons(text.text, Icons.Default.Edit, "Video Description", onChange)
}

@Composable
fun TimeFields(question: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(question)) }
    Spacer(Modifier.height(5.dp))

    TimeDataValueFields(text.text, "Add Duration", onChange)
}

@Composable
fun QuestionFields(question: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(question)) }
    Spacer(Modifier.height(20.dp))
    DataQuestionFields(text.text, "Question", onChange)
}

@Composable
fun AssertionFields(question: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(question)) }
    Spacer(Modifier.height(5.dp))

    DataValueFields(text.text, "Assertion", onChange)
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
            keyboardOptions = KeyboardOptions(keyboardType = if(description.equals("phone", true))KeyboardType.Number
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