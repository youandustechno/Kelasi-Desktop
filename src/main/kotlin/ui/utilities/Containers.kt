package ui.utilities

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FieldContainer(errorMessage: String, content: @Composable () () -> Unit) {
    Row {
        Column(
            Modifier.padding(end = 30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center) {
            content()
            Spacer(Modifier.height(5.dp))

            Row {
                Text(errorMessage,  style = MaterialTheme.typography.overline)
            }
        }
    }
}

@Composable
fun ButtonContainer(content: @Composable () () -> Unit) {
    Column(Modifier.wrapContentWidth()
        .wrapContentHeight()
        .padding(10.dp)) {
            content()
    }
}