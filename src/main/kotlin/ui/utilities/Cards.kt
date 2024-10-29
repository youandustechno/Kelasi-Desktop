package ui.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import models.video.VideoComponent

@Composable
fun UsersCards(content: @Composable() () -> Unit) {
    Cards {
        Column(Modifier
            .width(200.dp)
            .height(40.dp)
            .padding(1.dp)) {
            content()
        }
    }
}

@Composable
fun AddCoursesCards(content: @Composable() () -> Unit) {
    Cards {
        Column(Modifier
            .width(540.dp)
            .height(220.dp)
            .padding(3.dp)) {
            content()
        }
    }
}

@Composable
fun QuestionCards(content: @Composable() () -> Unit) {
    Cards {
        Column(Modifier
            .widthIn(300.dp, 600.dp)
            .wrapContentHeight()
            .padding(3.dp)) {
            content()
        }
    }
}

@Composable
fun SubscriptionCards(content: @Composable() () -> Unit) {
    Cards {
        Column(Modifier
            .width(200.dp)
            .height(80.dp)
            .padding(5.dp)) {
            content()
        }
    }
}

@Composable
fun DocCards(click: () -> kotlin.Unit, content: @Composable() () -> Unit) {
    CardsWrapHeightWithPadding (click = click ){
        Column(Modifier
            .width(200.dp)
            .wrapContentHeight()
            .defaultMinSize(minWidth = 150.dp, minHeight = 120.dp)) {
            content()
        }
    }
}

@Composable
fun CourseSubscriptionCard(content: @Composable() () -> Unit) {
    Cards {
        Column(Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp)) {
            content()
        }
    }
}

@Composable
fun ModuleCard(content: @Composable() () -> Unit) {
    Column(Modifier
        .width(300.dp)
        .height(120.dp)
        .padding(start = 2.dp, end = 2.dp)) {
        Cards {
            Column(Modifier
                .padding(5.dp)){
                content()
            }
        }
    }
}

@Composable
fun CourseCard(content: @Composable() () -> Unit) {
    Column(Modifier
        .width(350.dp)
        .height(150.dp)
        .padding(start = 5.dp, end = 5.dp)) {
        Cards {
            Column(Modifier
                .padding(5.dp)){
                content()
            }
        }
    }
}

@Composable
fun VideoPlayerCards( videoComponent: VideoComponent) {
    VideoCards {
        //Image
        VideoPlayerImpl(videoComponent)
    }
}

@Composable
fun VideoCard(content: @Composable() () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        VidCards {
            content()
        }
    }
}

@Composable
fun ErrorCard(content: @Composable() () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        ErrorCards {
            Column(Modifier
                .defaultMinSize(minHeight =100.dp)
                .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
              content()
            }
        }
    }
}


@Composable
fun ChangePasswordCard(content: @Composable() () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        UserCards {
            Column(Modifier
                .height(300.dp)
                .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                content()
            }
        }
    }
}

@Composable
fun PictureCard(content: @Composable() () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        UserCards {
            content()
        }
    }
}

@Composable
fun PersonalInformationCard(content: @Composable() () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        UserCards {
            Column(Modifier
                .fillMaxHeight()
                .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                content()
            }
        }
    }
}


@Composable
private fun VidCards(content:@Composable() () -> Unit) {

    Column (Modifier
        .border(width = 1.dp, color = Color(0XFFDEDBDC), shape = RoundedCornerShape(4.dp))
        .padding(1.dp)){
        Card {
            Column(Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Black)
                .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                content()
            }
        }
    }
}

@Composable
private fun ErrorCards(content:@Composable() () -> Unit) {

    Column (Modifier
        .border(width = 1.dp, color = Color(0XFFDEDBDC), shape = RoundedCornerShape(4.dp))
        .padding(1.dp)){
        Card {
            Column(Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0XFFDEDBDC))
                .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                content()
            }
        }
    }
}

@Composable
private fun Cards(content:@Composable() () -> Unit) {

    Column (Modifier
        .border(width = 1.dp, color = Color(0XFFDEDBDC), shape = RoundedCornerShape(4.dp))
        .padding(1.dp)){
        Card {
            Column(Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(2.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun VideoCards(content:@Composable() () -> Unit) {
    Card {
        Column(Modifier
            .fillMaxSize()
            .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            content()
        }
    }
}

@Composable
private fun CardsWrapHeightWithPadding(click: () ->Unit, content:@Composable() () -> Unit) {

    Column (Modifier
        .border(width = 1.dp, color = Color(0XFFDEDBDC), shape = RoundedCornerShape(4.dp))
        .padding(1.dp)
        .clickable { click.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Card {
            Column(Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp)
                .wrapContentHeight()
                .padding(5.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun UserCards(content:@Composable() () -> Unit) {

    Column (Modifier
        .border(width = 1.dp, color = Color(0XFFf7f7f7), shape = RoundedCornerShape(4.dp))
        .padding(1.dp)){
        Card {
            Column(Modifier
                .fillMaxSize()
                .background(Color(0XFFf7f7f7))
                .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                content()
            }
        }
    }
}