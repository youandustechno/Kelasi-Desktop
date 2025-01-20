package ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
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
import ui.Cache
import ui.Cache.userCache
import ui.LocalizedStrings
import ui.LocalizedStrings.PERSONAL_INFO
import ui.LocalizedStrings.UPDATE_BUTTON
import ui.LocalizedStrings.UPDATE_PICTURE
import ui.NavHelper
import ui.NavKeys.CONFIRM
import ui.NavKeys.EMAIL
import ui.NavKeys.EMPTY
import ui.NavKeys.FIRST
import ui.NavKeys.LAST
import ui.NavKeys.LEVEL
import ui.NavKeys.MIDDLE
import ui.NavKeys.PASSWORD
import ui.NavKeys.PHONE
import ui.NavKeys.USER_KEY
import ui.NavKeys.VALID
import ui.Route
import ui.utilities.*
import ui.utilities.FieldsValidation.isValid
import ui.utilities.FieldsValidation.isValidEmail
import ui.utilities.FieldsValidation.isValidLevel
import ui.utilities.FieldsValidation.isValidName
import ui.utilities.FieldsValidation.isValidPassword
import ui.utilities.FieldsValidation.isValidPhone
import ui.utilities.LoginUtils.UID
import java.io.File


@Composable
fun Settings(navHelper: NavHelper, onClick:((NavHelper) -> Unit)? = null) {

    val coroutineScope = rememberCoroutineScope()

    val settingsViewModel : SettingsViewModel by remember { mutableStateOf(SettingsViewModel()) }

    //PICTURE
    var fileToUpload by remember { mutableStateOf<String?>(null) }
    val imageBitmap by remember { derivedStateOf { fileToUpload?.let { loadImageBitmap(File(it)) } } }
    var urlImage by remember { mutableStateOf(userCache?.url?:EMPTY) }
    // loadImageBitmap(file: File)

    //CHANGE PASSWORD
    var passwordField by remember { mutableStateOf(EMPTY) }
    var newPasswordField by remember { mutableStateOf(EMPTY) }
    var confirmNewPasswordField by remember { mutableStateOf(EMPTY) }
    var isRegistration: Boolean by remember { mutableStateOf(false) }

    //PERSONAL INFORMATION
    var email by remember { mutableStateOf(userCache?.email?:EMPTY) }
    var phone by remember { mutableStateOf(userCache?.phoneNumber?:EMPTY) }
    var firstname by remember { mutableStateOf(userCache?.firstName?:EMPTY) }
    var lastname by remember { mutableStateOf(userCache?.lastName?:EMPTY) }
    var middlename by remember { mutableStateOf(userCache?.middleName?:EMPTY) }
    var level by remember { mutableStateOf(BaseValues.getStudentsLevels()[0]) }

    //VALIDATION
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var middleNameError by remember { mutableStateOf(false) }
    var levelError by remember { mutableStateOf(false) }

    //UPDATE CHECKER
    var isInfoUpdated : Boolean by remember { mutableStateOf(false) }

    LazyColumn(Modifier.padding(start = 32.dp, end = 32.dp)) {
        item {
            if(isInfoUpdated) {

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

                                     if(imageBitmap != null) {
                                        imageBitmap?.let {
                                            UserFileImageBitMap(it)
                                        }
                                    }
                                    else if(urlImage.isValid()) {
                                        UserImageUrl(urlImage)

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
                                            settingsViewModel.selectImageFile(it)
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
                                //Email
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

                                //Phone
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

                                //Password
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

                                if(isRegistration) {
                                    //New Password
                                    UserNewPasswordFields(newPasswordField, isError = passwordError) {
                                        newPasswordField = it
                                        passwordError = false
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                }

                                //Confirm Password
                                UserConfirmPasswordFields(confirmNewPasswordField, isRegistration = false, isError = passwordError ) {
                                    confirmNewPasswordField = it
                                    passwordError = false
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

                ConfirmButton(LocalizedStrings.get(UPDATE_BUTTON)) {
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
                            val userSuccess = userCache?.let {
                                settingsViewModel.updateUserInfo(
                                    UserDataModel(
                                        _id = it._id,
                                        firstName = firstname,
                                        lastName = lastname,
                                        middleName = middlename,
                                        level = level,
                                        email = email,
                                        phoneNumber = phone,
                                        password = passwordField,
                                        confirmPassword = confirmNewPasswordField
                                    ),
                                    UID
                                )
                            }
                            isInfoUpdated = true
                            delay(2000)

                            withContext(Dispatchers.Main) {

                                if(userSuccess?.user != null) {
                                    //userCache = userSuccess.user
                                    Cache.updateUser(userSuccess.user)
                                    val courseMap = mutableMapOf<String, Any>()
                                    userCache?.let {
                                        courseMap[USER_KEY] = it
                                    }
                                    isInfoUpdated = false
                                    onClick?.invoke(NavHelper(Route.Dashboard, courseMap))

                                }
                                else {
                                    //Display error to inform the user failed.

                                }
                            }
                        }
                    }
                }
                if(isInfoUpdated) {
                    Spacer(Modifier.width(8.dp))
                    ResourceImage50by50("image/icon_check.svg")
                }
            }
        }
    }
}

private fun validateEntries(
    firstName: String,
    lastName: String,
    middleName: String,
    level: String,
    email: String,
    phoneNumber:String,
    password: String,
    confirmPassword: String
): Map<String, Boolean> {

    val map = mutableMapOf<String, Boolean>()

    if(!firstName.isValid()) {
        map[FIRST] = false
    }

    if(!lastName.isValid()) {
        map[LAST] = false
    }

    if(!middleName.isValid()) {
        map[MIDDLE] = false
    }

    if(!level.isValid()) {
        map[LEVEL] = false
    }

    if(!email.isValidEmail()) {
        map[EMAIL] = false
    }

    if(!phoneNumber.isValid()) {
        map[PHONE] = false
    }

    if(!password.isValid()) {
        map[PASSWORD] = false
    }

    if(!confirmPassword.isValid() || password != confirmPassword) {
        map[CONFIRM] = false
    }

    if(map.keys.size == 0) {
        map[VALID] = true
    }

    return map
}