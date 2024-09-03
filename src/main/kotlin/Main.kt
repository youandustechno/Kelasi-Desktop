import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.AppTheme
import ui.GlobalContainer
import ui.auths.Login

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Mateya") {
       // App()
        //LoginPreview()
        AppTheme {
            GlobalContainer()
        }
    }
}

@Composable
@Preview
fun LoginPreview() {
    MaterialTheme {
        Login {}
    }
}
