package ui.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun TermsAndConditionsTexts() {
    CreateTexts(
        value ="TERMS AND CONDITIONS" ,
        style = MaterialTheme
            .typography
            .subtitle2
            .copy(color = Color(0xFF4A4A4A)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp))
}


@Composable
fun NavigationBarTitle(title: String,
                       timeLeft: String? = null,
                       isBackVisible: Boolean = false,
                       backPress:(() -> Unit)? = null) {
    Column(Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)) {
        Row(
            Modifier
                .zIndex(6F)
                .fillMaxWidth()
                .background(Color(0xFFD8C6BA))
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center) {
            CreateTexts(
                value = title ,
                style = MaterialTheme
                    .typography
                    .subtitle2
                    .copy(color = Color(0xFFFFFFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                TextAlign.Center)

            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f, true),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row {
                    if (timeLeft != null) {
                        CreateText(
                            value = timeLeft,
                            style = MaterialTheme.typography.subtitle2,
                            textAlignment = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    if(isBackVisible) {
                        Box(Modifier.clickable {
                            backPress?.invoke()
                        }) {
                            Image(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Back")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateTexts(value: String,
                style: TextStyle,
                modifier: Modifier,
                textAlignment: TextAlign? = null) {
    CreateText(value = value, style = style, textAlignment)
}

@Composable
fun CreateText(value: String, style: TextStyle, textAlignment: TextAlign?) {
    Text(text = value, style = style, textAlign = textAlignment)
}

@Composable
fun SectionTitles(sectionName : String) {
    Text(sectionName, style = MaterialTheme
        .typography.subtitle1
        .copy(fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight(500),
            lineHeight = 20.sp
        )
    )
}