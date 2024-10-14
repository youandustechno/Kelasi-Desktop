package ui.utilities

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SectionTitle(sectionName : String) {
    Text(sectionName, style = MaterialTheme
        .typography.h5
        .copy(fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight(500),
            lineHeight = 20.sp
        )
    )
}