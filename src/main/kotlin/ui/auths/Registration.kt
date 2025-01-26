package ui.auths

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.BaseValues
import models.auth.UserDataModel
import ui.LocalizedStrings
import ui.LocalizedStrings.ALREADY_HAVE_ACCOUNT
import ui.LocalizedStrings.PERSONAL_INFO
import ui.LocalizedStrings.REGISTER
import ui.LocalizedStrings.UPDATE_PICTURE
import ui.NavHelper
import ui.NavKeys.EMPTY
import ui.NavKeys.USER_KEY
import ui.Route
import ui.utilities.*
import ui.utilities.FieldsValidation.isValid
import ui.utilities.FieldsValidation.isValidEmail
import ui.utilities.FieldsValidation.isValidLevel
import ui.utilities.FieldsValidation.isValidName
import ui.utilities.FieldsValidation.isValidPassword
import ui.utilities.FieldsValidation.isValidPhone
import java.io.File

@Composable
fun Registration(navHelper: NavHelper, onClick:((NavHelper) -> Unit)? = null) {

    val coroutineScope = rememberCoroutineScope()

    val authViewModel : AuthViewModel by remember { mutableStateOf(AuthViewModel()) }

    //PICTURE
    var fileToUpload by remember { mutableStateOf<String?>(null) }
    val imageBitmap by remember { derivedStateOf { fileToUpload?.let { loadImageBitmap(File(it)) } } }
    var urlImage by remember { mutableStateOf(EMPTY) }
    // loadImageBitmap(file: File)

    //CHANGE PASSWORD
    var passwordField by remember { mutableStateOf(EMPTY) }
    var confirmNewPasswordField by remember { mutableStateOf(EMPTY) }

    //PERSONAL INFORMATION
    var email by remember { mutableStateOf(EMPTY) }
    var phone by remember { mutableStateOf(EMPTY) }
    var firstname by remember { mutableStateOf(EMPTY) }
    var lastname by remember { mutableStateOf(EMPTY) }
    var middlename by remember { mutableStateOf(EMPTY) }
    var level by remember { mutableStateOf(BaseValues.getStudentsLevels()[0]) }
    //var errorType by remember { mutableStateOf(LoginErrorState.None) }

    //VALIDATION
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var middleNameError by remember { mutableStateOf(false) }
    var levelError by remember { mutableStateOf(false) }

    var passwordErrorMessage by remember { mutableStateOf(EMPTY) }
    var confirmPassErrorMessage by remember { mutableStateOf(EMPTY) }
    var emailErrorMessage by remember { mutableStateOf(EMPTY) }
    var phoneErrorMessage by remember { mutableStateOf(EMPTY) }


    LazyColumn(Modifier.padding(start = 32.dp, end = 32.dp)) {
        item {
            LinkButton(LocalizedStrings.get(ALREADY_HAVE_ACCOUNT)) {
                onClick?.invoke(NavHelper(Route.AuthLogin))
            }
        }
        item {
            Row(Modifier.fillMaxSize()) {
                Column(Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    //.height(300.dp)
                    .weight(1F)) {
                    //Picture
                    Row(Modifier.fillMaxWidth()
                        .defaultMinSize(minHeight = 300.dp)
                        .fillMaxHeight()) {
                        PictureCard {
                            Box(Modifier.fillMaxSize()) {
                                Box (Modifier.fillMaxSize()){
                                    UserBackgroundImageUrl{}
                                }

                                Row(Modifier.fillMaxWidth()
                                    .wrapContentHeight()
                                    .align(Alignment.CenterStart),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(Modifier.width(10.dp))
                                    if(urlImage.isValid()) {

                                        UserImageUrl(urlImage)

                                    } else if(imageBitmap != null) {
                                        imageBitmap?.let {
                                            UserFileImageBitMap(it)
                                        }
                                    }
                                    else {
                                        ResourceUserImage("image/icon_person.svg") {}
                                    }

                                    Spacer(Modifier.width(8.dp))
                                    UserPictureButton(LocalizedStrings.get(UPDATE_PICTURE)) {
                                        fileToUpload = showImageFileChooser()
                                    }
                                    if(!fileToUpload.isNullOrEmpty()) {
                                        fileToUpload?.let {
                                            authViewModel.selectImageFile(it)
                                            Spacer(Modifier.width(8.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.width(20.dp))
                Column(Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1F)) {
                    //Personal Information
                    Row (Modifier.fillMaxWidth()
                        .fillMaxHeight()) {
                        PersonalInformationCard {
                            Column(Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(20.dp)) {

                                SectionTitle(LocalizedStrings.get(PERSONAL_INFO))

                                Spacer(modifier = Modifier.height(16.dp))
                                // Name Input
                                FirstNameFields(firstname, firstNameError) {
                                    firstname = it
                                    firstNameError = false
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                LastNameFields(lastname, lastNameError) {
                                    lastname = it
                                    lastNameError = false
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                MiddleFields(middlename, middleNameError) {
                                    middlename = it
                                    middleNameError = false
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Card(Modifier.fillMaxWidth()
                                    .wrapContentHeight(),
                                    elevation = 6.dp
                                ) {
                                    DropDown(BaseValues.getStudentsLevels()) {
                                        level = it
                                        levelError = false
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                // Card Number Input
                                UserEmailFields(email, emailError) {
                                    email = it
                                    emailError = false
                                }
                                if(emailError) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    DisplayError(ErrorState.Email)
                                    Spacer(modifier = Modifier.height(5.dp))
                                } else {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                                // Card Number Input
                                UserPhoneFields(phone, phoneError) {
                                    phone = it
                                    phoneError = false
                                }
                                if(phoneError) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    DisplayError(ErrorState.Phone)
                                    Spacer(modifier = Modifier.height(5.dp))
                                } else {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                                // Name Input
                                UserPasswordFields(passwordField, passwordError) {
                                    passwordField = it
                                    passwordError = false
                                }

                                if(passwordError) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    DisplayError(ErrorState.Password)
                                    Spacer(modifier = Modifier.height(5.dp))
                                } else {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                                // Card Number Input
                                UserConfirmPasswordFields(confirmNewPasswordField, isRegistration = true, isError = passwordError) {
                                    confirmNewPasswordField = it
                                    confirmPasswordError = false
                                }

                                if(confirmPasswordError) {
                                    DisplayError(ErrorState.ConfirmPassword)
                                }
                            }
                        }
                    }
                }
            }
        }

        item {

            Spacer(modifier = Modifier.height(15.dp))
            // Pay Button
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                ConfirmButton(LocalizedStrings.get(REGISTER)) {
                   // onClick?.invoke(NavHelper(Route.Payments))
                    if(!phone.isValidPhone()) {
                        phoneError = true
                    }

                    if(!email.isValidEmail()) {
                        emailError = true
                    }

                    if(!passwordField.isValidPassword()) {
                        passwordError = true
                    }

                    if(!confirmNewPasswordField.isValidPassword() && confirmNewPasswordField != passwordField) {
                        confirmPasswordError = true
                    }

                    if(!firstname.isValidName()) {
                        firstNameError = true
                    }

                    if(!middlename.isValidName()) {
                        middleNameError = true
                    }

                    if(!lastname.isValidName()) {
                        lastNameError = true
                    }

                    if(!email.isValidEmail()) {
                        emailError = true
                    }

                    if(!level.isValidLevel()) {
                        levelError = true
                    }

                    if(phone.isValidPhone()
                        && email.isValidEmail()
                        && passwordField.isValidPassword()
                        && (confirmNewPasswordField.isValidPassword() && confirmNewPasswordField == passwordField)
                        && firstname.isValidName()
                        && level.isValidLevel()
                        && middlename.isValidName()
                        && lastname.isValidName()) {

                        coroutineScope.launch(Dispatchers.IO) {
                            val userSuccess = authViewModel.startRegistration(
                                UserDataModel(
                                    firstName = firstname,
                                    lastName = lastname,
                                    middleName = middlename,
                                    level = level,
                                    email = email,
                                    phoneNumber = phone,
                                    password = passwordField,
                                    confirmPassword = confirmNewPasswordField
                                )
                            )
                            delay(500L)
                            withContext(Dispatchers.Main) {

                                if(userSuccess.user != null) {
                                    val courseMap = mutableMapOf<String, Any>()
                                    courseMap[USER_KEY] = userSuccess.user
                                    onClick?.invoke(NavHelper(Route.Dashboard, courseMap))
                                }
                                else {
                                    //Display error to inform the user failed.

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}