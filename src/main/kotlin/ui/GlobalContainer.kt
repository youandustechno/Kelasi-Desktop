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
import ui.auths.Login
import ui.auths.OrgAuth
import ui.dashboards.Dashboard
import ui.groups.Group
import ui.manages.*
import ui.organizations.CoursesSubscriptionList
import ui.organizations.Subscriptions
import ui.organizations.UsersSubscription
import ui.quizzes.ManageQuizzes
import ui.quizzes.Quiz
import ui.utilities.FieldsValidation.isValid
import ui.utilities.Headers
import ui.utilities.TabButton
import ui.videos.Documents
import ui.videos.Videos
import java.util.prefs.Preferences

class App

@Composable
fun GlobalContainer() {

    var groupCode by remember { mutableStateOf("") }
    //val prefs = Preferences.userNodeForPackage(App::class.java)

    LaunchedEffect(Unit) {
        groupCode = try {
            StorageHelper().retrieveFromStorage(StorageHelper.AUTH_CODE)!!
        } catch (exc: Exception) {
            ""
        }
    }

    var navigationState by remember { mutableStateOf(if(groupCode.isValid()) {
         NavHelper(Route.AuthLogin)
    } else  {
        NavHelper(Route.AuthOrg)
    })}

    var coursesGlobalList : CoursesListResponse? by remember { mutableStateOf(CoursesListResponse())}

    var authState by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource("image/black_people.webp"),
            contentDescription = "people",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Row(Modifier.fillMaxSize()){

            when(navigationState.route) {

                Route.AuthOrg -> {
                    ContentWrapper(false, navigationState, {}) {
                        OrgAuth { org ->
                            navigationState = NavHelper(Route.AuthLogin, )
                        }
                    }
                }
                Route.AuthLogin -> {
                    ContentWrapper(false,navigationState, {}) {
                        Login { loggedIn ->
                            if(loggedIn) {
                                navigationState = NavHelper(Route.Dashboard)
                            } else {
                                navigationState = NavHelper(Route.AuthLogin)
                            }
                            authState = loggedIn
                        }
                    }
                }
                Route.Dashboard -> {
                    ContentWrapper(true, navigationState, {
                        navigationState = it
                    }) {
                        Dashboard({ courses ->
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
                Route.ManageQuizzes -> {
                    ContentWrapper(true, navigationState, {
                        navigationState = it
                    }) {
                        ManageQuizzes(navigationState) { quiz ->
                            navigationState = quiz
                        }
                    }
                }
                Route.ManageVideo -> {
                    ContentWrapper(true, navigationState, {
                        navigationState = it
                    }) {
                        ManageVideos(navigationState)
                    }
                }
                Route.VideosList -> {
                    ContentWrapper(true, navigationState,{
                        navigationState = it
                    }) {
                        Videos (coursesGlobalList,
                            navigationState, { courses ->
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

    var courseId by remember { mutableStateOf("") }
    var course : CourseComponent? by remember { mutableStateOf(null) }

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
                        Text("Home", style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.ManageVideo) {
                        Text("Manage Videos", style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.Quiz) {
                        Text("Quiz", style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.ManageQuizzes) {
                        Text("Manage Quizzes", style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.VideosList) {
                        Text("Videos", style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.Subscriptions) {
                        Text("Settings", style = MaterialTheme.typography.h6)
                    }
                    else if(navigationState.route == Route.ViewDocument) {
                        Text("Module Documents", style = MaterialTheme.typography.h6)
                    }
                    else {
                        Text("", style = MaterialTheme.typography.h6)
                    }
                }

                Row(Modifier
                    .align(Alignment.CenterEnd)
                    .wrapContentWidth()
                    .wrapContentHeight()) {

                    if(navigationState.route == Route.Dashboard) {
                        TabButton("Settings") {
                            //Todo Settings
                            onClick.invoke(NavHelper(Route.Subscriptions))
                        }

                        TabButton("Logout") {
                            onClick.invoke(NavHelper(Route.AuthLogin))
                        }
                    }
                    else if(navigationState.route == Route.ManageVideo) {
                        courseId = navigationState.dataMap["courseId"].toString()

                        TabButton("Home") {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton("Videos") {
                            val courseMap = mutableMapOf<String, Any>()
                            courseMap["courseId"] = courseId
                            onClick.invoke(NavHelper(Route.VideosList, courseMap))
                        }
                    }
                    else if(navigationState.route == Route.Quiz) {

                        if(navigationState.dataMap.containsKey("course")) {
                            course = navigationState.dataMap["course"] as CourseComponent

                        } else if(navigationState.dataMap.containsKey("courseId")) {
                            courseId = navigationState.dataMap["courseId"].toString()
                        }

                        TabButton("Home") {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton("Videos") {
                            val courseMap = mutableMapOf<String, Any>()
                            if(course != null) {
                                courseMap["course"] = course!!

                            } else if(courseId.isNotEmpty()) {
                                courseMap["courseId"] = courseId
                            }
                            onClick.invoke(NavHelper(Route.VideosList, courseMap))
                        }
                    }
                    else if(navigationState.route == Route.ManageQuizzes) {

                        if(navigationState.dataMap.containsKey("course")) {
                            course = navigationState.dataMap["course"] as CourseComponent

                        } else if(navigationState.dataMap.containsKey("courseId")) {
                            courseId = navigationState.dataMap["courseId"].toString()
                        }

                        TabButton("Home") {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton("Videos") {
                            val courseMap = mutableMapOf<String, Any>()
                            if(course != null) {
                                courseMap["course"] = course!!

                            } else if(courseId.isNotEmpty()) {
                                courseMap["courseId"] = courseId
                            }
                            onClick.invoke(NavHelper(Route.VideosList, courseMap))
                        }
                    }
                    else if(navigationState.route == Route.VideosList) {

                        if(navigationState.dataMap.containsKey("course")) {
                            course = navigationState.dataMap["course"] as CourseComponent

                        } else if(navigationState.dataMap.containsKey("courseId")) {
                            courseId = navigationState.dataMap["courseId"].toString()
                        }

                        TabButton("Home") {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton("Logout") {
                            onClick.invoke(NavHelper(Route.AuthLogin))
                        }
                    }
                    else if(navigationState.route == Route.Subscriptions) {
                        TabButton("Home") {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton("Logout") {
                            onClick.invoke(NavHelper(Route.AuthLogin))
                        }
                    }
                    else if(navigationState.route == Route.ViewDocument) {

                        if(navigationState.dataMap.containsKey("course")) {
                            course = navigationState.dataMap["course"] as CourseComponent

                        } else if(navigationState.dataMap.containsKey("courseId")) {
                            courseId = navigationState.dataMap["courseId"].toString()
                        }

                        TabButton("Home") {
                            onClick.invoke(NavHelper(Route.Dashboard))
                        }

                        TabButton("Videos") {
                            val courseMap = mutableMapOf<String, Any>()
                            if(course != null) {
                                courseMap["course"] = course!!

                            } else if(courseId.isNotEmpty()) {
                                courseMap["courseId"] = courseId
                            }
                            onClick.invoke(NavHelper(Route.VideosList, courseMap))
                        }
                    }
                    else {
                    }
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
    AuthOrg ("Org"),
    Organization("Organization"),
    Quiz("Quiz"),
    ViewDocument("UploadDocument"),
    ManageQuizzes("Quizzes"),
    VideosList("CoursesList"),
    UserSubscription("UserSub"),
    Subscriptions("Subscription"),
    CourseSubscriptions("CourseSub"),
    ManageVideo("ManageVideo"),
    Dashboard("ManageVideo")
}

