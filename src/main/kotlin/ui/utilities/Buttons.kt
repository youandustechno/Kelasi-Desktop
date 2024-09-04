package ui.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.fillMaxWidth()
                .defaultMinSize(minWidth = 50.dp, minHeight = 30.dp )
                //.background(Color.Green)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}

@Composable
fun TabButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.width(150.dp)
                .defaultMinSize(minWidth = 50.dp, minHeight = 10.dp )
                .background(Color(0XFF5ACA0))
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun NavigationButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.fillMaxWidth()
                .defaultMinSize(minWidth = 150.dp, minHeight = 30.dp )
                //.background(Color.Green)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}

@Composable
fun SubmitQuizButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.fillMaxWidth()
                .height(30.dp)
                .padding( top = 0.dp, bottom = 0.dp)) {
            Text(value, style =  MaterialTheme.typography.caption.copy(fontSize = 10.sp))
        }
    }
}

@Composable
fun PlayStopButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.width(100.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(50.dp))
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}


@Composable
fun ConfirmButton(value: String, click: () -> Unit) {

    Row {
        Button( { click()},
            modifier = Modifier.width(300.dp)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}

@Composable
fun ManageDeleteButton(value: String, click: () -> Unit) {
    Row {
        Button( { click()},
            modifier = Modifier.width(450.dp)
                //.background(Color.Green)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}

@Composable
fun DenyButton(value: String, click: () -> Unit) {
    Row {
        Button( { click()},
            modifier = Modifier.width(150.dp)
                //.background(Color.Green)
                .padding(5.dp)) {

            Text(value, style =  MaterialTheme.typography.button)
        }
    }
}