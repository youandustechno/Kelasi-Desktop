package ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.CoursesListResponse
import models.auth.UserDataModel
import models.video.CourseComponent
import models.video.VideoComponent
import ui.NavHelper
import ui.Route
import ui.utilities.*
import ui.utilities.FieldsValidation.isValid
import ui.videos.VideosViewModel
import java.io.File

@Composable
fun Settings(navHelper: NavHelper, onClick:((NavHelper) -> Unit)? = null) {

    val coroutineScope = rememberCoroutineScope()

    val settingsViewModel : SettingsViewModel by remember { mutableStateOf(SettingsViewModel()) }

    //PICTURE
    var fileToUpload by remember { mutableStateOf<String?>(null) }
    val imageBitmap by remember { derivedStateOf { fileToUpload?.let { loadImageBitmap(File(it)) } } }
    var urlImage by remember { mutableStateOf("") }
    // loadImageBitmap(file: File)

    //CHANGE PASSWORD
    var passwordField by remember { mutableStateOf("") }
    var newPasswordField by remember { mutableStateOf("") }
    var confirmNewPasswordField by remember { mutableStateOf("") }
    var isRegistration: Boolean by remember { mutableStateOf(false) }

    //PERSONAL INFORMATION
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var middlename by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }

    LazyColumn(Modifier.padding(start = 32.dp, end = 32.dp)) {
        item {
            // TODO Add subscription row to take with a click in subscription screen to select type of subscription.
            // And also show message if subscription expired.
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
                                    UserPictureButton("Update Picture") {
                                        fileToUpload = showFileChooser()
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

                                SectionTitle("Personal Information")

                                Spacer(modifier = Modifier.height(16.dp))
                                // Name Input
                                FirstNameFields(firstname) {
                                    firstname = it
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                LastNameFields(lastname) {
                                    lastname = it
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                MiddleFields(middlename) {
                                    middlename = it
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                LevelFields(level) {
                                    level = it
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                // Card Number Input
                                UserEmailFields(email) {
                                    email = it
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                // Card Number Input
                                UserPhoneFields(phone) {
                                    phone = it
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                // Name Input
                                UserPasswordFields(passwordField) {
                                    passwordField = it
                                }
                                if(isRegistration) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    // Card Number Input
                                    UserNewPasswordFields(newPasswordField) {
                                        newPasswordField = it
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                // Card Number Input
                                UserConfirmPasswordFields(confirmNewPasswordField) {
                                    confirmNewPasswordField = it
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

                ConfirmButton("REGISTER") {
                   // onClick?.invoke(NavHelper(Route.Payments))
                    val validDataMap = validateEntries(
                        firstName = firstname,
                        lastName = lastname,
                        middleName = middlename,
                        level = level,
                        email = email,
                        phoneNumber = phone,
                        password = passwordField,
                        confirmPassword = confirmNewPasswordField
                    )
                    if(validDataMap.containsKey("valid")) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val userSuccess = settingsViewModel.updateUserInfo(
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
                            withContext(Dispatchers.Main) {

                                if(userSuccess.user != null) {
                                    val courseMap = mutableMapOf<String, Any>()
                                    courseMap["user"] = userSuccess.user
                                    onClick?.invoke(NavHelper(Route.Dashboard, courseMap))
                                }
                                else {
                                    //Display error to inform the user failed.

                                }
                            }
                        }

                    } else {
                        //Handle validation
                    }
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
        map["first"] = false
    }

    if(!lastName.isValid()) {
        map["last"] = false
    }

    if(!middleName.isValid()) {
        map["middle"] = false
    }

    if(!level.isValid()) {
        map["middle"] = false
    }

    if(!email.isValid()) {
        map["email"] = false
    }

    if(!phoneNumber.isValid()) {
        map["phone"] = false
    }

    if(!password.isValid()) {
        map["password"] = false
    }

    if(!confirmPassword.isValid() || password != confirmPassword) {
        map["confirm"] = false
    }

    if(map.keys.size == 0) {
        map["valid"] = true
    }

    return map
}