package ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import models.CoursesListResponse
import models.auth.UserDataModel
import models.video.CourseComponent
import models.video.VideoComponent
import ui.Cache
import ui.Cache.userCache
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
import ui.videos.VideosViewModel
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
    var level by remember { mutableStateOf(userCache?.level?:EMPTY) }

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

                ConfirmButton("UPDATE") {
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
                    if(validDataMap.containsKey(VALID)) {
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
                                    )
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

                    } else {
                        //Handle validation
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

    if(!email.isValid()) {
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