package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import helpers.StorageHelper
import models.CoursesListResponse
import models.video.CourseComponent
import ui.LocalizedStrings.AUTH_CODE
import ui.LocalizedStrings.DOCUMENTS
import ui.LocalizedStrings.HOME
import ui.LocalizedStrings.LOGOUT
import ui.LocalizedStrings.MANAGE_QUIZZES
import ui.LocalizedStrings.MANAGE_VIDEOS
import ui.LocalizedStrings.QUIZ
import ui.LocalizedStrings.REGISTER
import ui.LocalizedStrings.SETTINGS
import ui.LocalizedStrings.VIDEOS
import ui.NavKeys.COURSE
import ui.NavKeys.COURSE_ID
import ui.NavKeys.EMPTY
import ui.auths.HelpLogin
import ui.auths.Login
import ui.auths.OrgAuth
import ui.auths.Registration
import ui.dashboards.Dashboard
import ui.groups.Group
import ui.organizations.CoursesSubscriptionList
import ui.organizations.Subscriptions
import ui.organizations.UsersSubscription
import ui.quizzes.Quiz
import ui.settings.Settings
import ui.terms.TermsAndConditions
import ui.utilities.FieldsValidation.isValid
import ui.utilities.Headers
import ui.utilities.TabButton
import ui.videos.Documents
import ui.videos.Videos


class App

@Composable
fun GlobalContainer() {

    var groupCode by remember { mutableStateOf(EMPTY) }
    //val prefs = Preferences.userNodeForPackage(App::class.java)

    var navigationState by remember { mutableStateOf(NavHelper(Route.None))}

    LaunchedEffect(groupCode) {
        groupCode = try {
            StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)!!
        } catch (exc: Exception) {
            EMPTY
        }
        navigationState = if(groupCode.isValid()) {
            NavHelper(Route.AuthLogin)
        } else  {
            NavHelper(Route.Terms)
        }
    }

    var coursesGlobalList : CoursesListResponse? by remember { mutableStateOf(CoursesListResponse()) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource("image/black_people.webp"),
            contentDescription = "people",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Row(Modifier.fillMaxSize()){

            when(navigationState.route) {

                Route.AuthOrg-> {
                    ContentWrapper(false, navigationState, {}) {
                        OrgAuth { nav ->
                            navigationState = nav
                        }
                    }
                }
                Route.Terms -> {
                    ContentWrapper(false, navigationState, {}) {
                        TermsAndConditions {
                            navigationState = NavHelper(Route.AuthLogin)
                        }
                    }
                }
                Route.AuthLogin -> {
                    ContentWrapper(false, navigationState, {}) {
                        Login { navHelper ->
                            navigationState = navHelper
                        }
                    }
                }
                Route.HelpLogin -> {
                    ContentWrapper(false, navigationState, {}) {
                        HelpLogin { navHelper ->
                            navigationState = navHelper
                        }
                    }
                }
                Route.Dashboard -> {
                    ContentWrapper(true, navigationState, {
                        navigationState = it
                    }) {
                        Dashboard(navigationState, { courses ->
                            coursesGlobalList = courses
                        }) {
                            navigationState = it
                        }
                    }
                }
                Route.Organization -> {
                    ContentWrapper(true, navigationState, {
                        navigationState = it
                    }) {
                        Group ({ Headers("Groups") }) {

                        }
                    }
                }
                Route.Quiz -> {
                    ContentWrapper(true, navigationState, {
                        navigationState = it
                    }) {
                        Quiz(navigationState) { quiz ->
                            navigationState = quiz
                        }
                    }
                }
//                Route.ManageQuizzes -> {
//                    ContentWrapper(true, navigationState, {
//                        navigationState = it
//                    }) {
//                        ManageQuizzes(navigationState) { quiz ->
//                            navigationState = quiz
//                        }
//                    }
//                }
                Route.VideosList -> {
                    ContentWrapper(true, navigationState,{
                        navigationState = it
                    }) {
                        Videos (coursesGlobalList, navigationState, { courses ->
                                coursesGlobalList = courses
                            }) { videos->
                            navigationState = videos
                        }
                    }
                }
                Route.UserSubscription -> {
                    ContentWrapper(true, navigationState,{
                        navigationState = it
                    }) {
                        UsersSubscription { editUsers ->
                            navigationState = editUsers
                        }
                    }
                }
                Route.Subscriptions-> {
                    ContentWrapper(true, navigationState,{
                        navigationState = it
                    }) {
                        CoursesSubscriptionList(onClick = { editCourses ->
                            navigationState = editCourses
                        })
                    }

                }
                Route.CourseSubscriptions -> {
                    //Registered Organizations
                    ContentWrapper(true, navigationState, {
                        navigationState = it
                    }) {
                        Subscriptions { action ->
                            navigationState = action
                        }
                    }
                    //Registration
                }
                Route.Register -> {
                    ContentWrapper(true, navigationState,{
                        navigationState = it
                    }) {
                        Registration(navigationState) {
                            navigationState = it
                        }
                    }
                }
                Route.Settings -> {
                    ContentWrapper(true, navigationState,{
                        navigationState = it
                    }) {
                        Settings(navigationState) {
                            navigationState = it
                        }
                    }
                }
                Route.ViewDocument -> {
                    ContentWrapper(true, navigationState, {
                        navigationState = it
                    }) {
                        Documents(navigationState) {

                        }
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun ContentWrapper(withNav: Boolean, navigationState: NavHelper, onClick:(NavHelper) -> Unit, content: @Composable () () -> Unit) {

    var courseId by remember { mutableStateOf(EMPTY) }
    var course : CourseComponent? by remember { mutableStateOf(null) }
    val globalViewModel by mutableStateOf(GlobalViewModel())
    var logoutIsClick by remember { mutableStateOf(false) }

    if(logoutIsClick) {
       LaunchedEffect(Unit) {
           val isLoggedOut = globalViewModel.startLogout()
           if(isLoggedOut) {
             onClick.invoke(NavHelper(Route.AuthLogin))
           }
           else {
            //Todo tell the user the issue
             onClick.invoke(NavHelper(Route.AuthLogin))
          }
           logoutIsClick = false
       }
    }

    if(withNav) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Box (Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(10.dp)
                .background(Color(0XFFf7f7f7))) {

                Box(Modifier
                    .align(Alignment.CenterStart)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(start = 20.dp)) {

                    if(navigationState.route == Route.Dashboard) {
                        Text(LocalizedStrings.get(HOME), style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.ManageVideo) {
                        Text(LocalizedStrings.get(MANAGE_VIDEOS), style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.Quiz) {
                        Text(LocalizedStrings.get(QUIZ), style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.ManageQuizzes) {
                        Text(LocalizedStrings.get(MANAGE_QUIZZES), style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.VideosList) {
                        Text(LocalizedStrings.get(VIDEOS), style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.Register) {
                        Text(LocalizedStrings.get(REGISTER), style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.Settings) {
                        Text(LocalizedStrings.get(SETTINGS), style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.ViewDocument) {
                        Text(LocalizedStrings.get(DOCUMENTS), style = MaterialTheme.typography.h6)
                    }
                    else {
                        Text(EMPTY, style = MaterialTheme.typography.h6)
                    }
                }

                Row(Modifier
                    .align(Alignment.CenterEnd)
                    .wrapContentWidth()
                    .wrapContentHeight()) {

                    if(navigationState.route == Route.Dashboard) {
                        TabButton(LocalizedStrings.get(SETTINGS)) {
                            //Todo Settings
                            onClick.invoke(NavHelper(Route.Settings))
                        }

                        TabButton(LocalizedStrings.get(LOGOUT)) {
                            logoutIsClick = true
                        }
                    }
                    else if(navigationState.route == Route.ManageVideo) {
                        courseId = navigationState.dataMap[COURSE_ID].toString()

                        TabButton(LocalizedStrings.get(HOME)) {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton(LocalizedStrings.get(VIDEOS)) {
                            val courseMap = mutableMapOf<String, Any>()
                            courseMap[COURSE_ID] = courseId
                            onClick.invoke(NavHelper(Route.VideosList, courseMap))
                        }
                    }
                    else if(navigationState.route == Route.Quiz) {

                        if(navigationState.dataMap.containsKey(COURSE)) {
                            course = navigationState.dataMap[COURSE] as CourseComponent

                        } else if(navigationState.dataMap.containsKey(COURSE_ID)) {
                            courseId = navigationState.dataMap[COURSE_ID].toString()
                        }

                        TabButton(LocalizedStrings.get(HOME)) {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton(LocalizedStrings.get(VIDEOS)) {
                            val courseMap = mutableMapOf<String, Any>()
                            if(course != null) {
                                courseMap[COURSE] = course!!

                            } else if(courseId.isNotEmpty()) {
                                courseMap[COURSE_ID] = courseId
                            }
                            onClick.invoke(NavHelper(Route.VideosList, courseMap))
                        }
                    }
                    else if(navigationState.route == Route.ManageQuizzes) {

                        if(navigationState.dataMap.containsKey(COURSE)) {
                            course = navigationState.dataMap[COURSE] as CourseComponent

                        } else if(navigationState.dataMap.containsKey(COURSE_ID)) {
                            courseId = navigationState.dataMap[COURSE_ID].toString()
                        }

                        TabButton(LocalizedStrings.get(HOME)) {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton(LocalizedStrings.get(VIDEOS)) {
                            val courseMap = mutableMapOf<String, Any>()
                            if(course != null) {
                                courseMap[COURSE] = course!!

                            } else if(courseId.isNotEmpty()) {
                                courseMap[COURSE_ID] = courseId
                            }
                            onClick.invoke(NavHelper(Route.VideosList, courseMap))
                        }
                    }
                    else if(navigationState.route == Route.VideosList) {

                        if(navigationState.dataMap.containsKey(COURSE)) {
                            course = navigationState.dataMap[COURSE] as CourseComponent

                        } else if(navigationState.dataMap.containsKey(COURSE_ID)) {
                            courseId = navigationState.dataMap[COURSE_ID].toString()
                        }

                        TabButton(LocalizedStrings.get(HOME)) {
                            val courseMap = mutableMapOf<String, Any>()
                            if(course != null) {
                                courseMap[COURSE] = course!!

                            } else if(courseId.isNotEmpty()) {
                                courseMap[COURSE_ID] = courseId
                            }
                            onClick.invoke(NavHelper(Route.Dashboard, courseMap))
                        }

                        TabButton(LocalizedStrings.get(LOGOUT)) {
                            logoutIsClick = true
                        }
                    }
                    else if(navigationState.route == Route.Subscriptions) {
                        TabButton(LocalizedStrings.get(HOME)) {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton(LocalizedStrings.get(LOGOUT)) {
                            logoutIsClick = true
                        }
                    }
                    else if(navigationState.route == Route.ViewDocument) {

                        if(navigationState.dataMap.containsKey(COURSE)) {
                            course = navigationState.dataMap[COURSE] as CourseComponent

                        } else if(navigationState.dataMap.containsKey(COURSE_ID)) {
                            courseId = navigationState.dataMap[COURSE_ID].toString()
                        }

                        TabButton(LocalizedStrings.get(HOME)) {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton(LocalizedStrings.get(VIDEOS)) {
                            val courseMap = mutableMapOf<String, Any>()
                            if(course != null) {
                                courseMap[COURSE] = course!!

                            } else if(courseId.isNotEmpty()) {
                                courseMap[COURSE_ID] = courseId
                            }
                            onClick.invoke(NavHelper(Route.VideosList, courseMap))
                        }
                    }
                    else if(navigationState.route == Route.Settings) {
                        TabButton(LocalizedStrings.get(HOME)) {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton(LocalizedStrings.get(LOGOUT)) {
                            logoutIsClick = true
                        }
                    }
                    else if(navigationState.route == Route.Register) {

                        TabButton(LocalizedStrings.get(AUTH_CODE)) {
                            onClick.invoke(NavHelper(Route.Terms))
                        }
                    }
                    else { }
                }

            }
            Column (Modifier
                .fillMaxHeight()
                .padding(5.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center) {
                content.invoke()
            }
        }

    } else {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            content()
        }
    }
}

data class NavHelper(val route: Route, val dataMap: MutableMap<String, Any> = mutableMapOf())

enum class Route(route: String) {
    AuthLogin("Login"),
    HelpLogin("HelpLogin"),
    Terms ("Terms"),
    AuthOrg ("Org"),
    Organization("Organization"),
    Quiz("Quiz"),
    ViewDocument("UploadDocument"),
    Register("Register"),
    Settings("Settings"),
    ManageQuizzes("Quizzes"),
    VideosList("CoursesList"),
    UserSubscription("UserSub"),
    Subscriptions("Subscription"),
    CourseSubscriptions("CourseSub"),
    ManageVideo("ManageVideo"),
    Dashboard("ManageVideo"),
    None("None")
}
