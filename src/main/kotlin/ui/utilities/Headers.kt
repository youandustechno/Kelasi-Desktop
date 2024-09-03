package ui.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Headers(pageName: String) {
    Column (
        Modifier.fillMaxWidth()
            .height(80.dp)
            .padding(top = 30.dp)
        //.verticalScroll(rememberScrollState())
        //.wrapContentHeight()
        //.weight(weight = 1f, fill = false)
    ) {
        Box(
            Modifier.width(200.dp)
                .height(40.dp)
                .padding(5.dp)
                .background(color = Color.Blue, shape = RoundedCornerShape(5.dp))
                .align(Alignment.End)
        ) {

            Column(
                Modifier.fillMaxWidth()
                    .height(70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(pageName)
            }
        }

        Spacer(Modifier.height(50.dp))
        Row {
            Text("ADD")
        }
        Spacer(Modifier.height(10.dp))
    }
}