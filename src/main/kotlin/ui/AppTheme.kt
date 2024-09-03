package ui


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightThemeColors = lightColors(
    primary = Color(0xFFc5aca0),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC5)
)

val DarkThemeColors = darkColors(
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC5)
)

@Composable
fun AppTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}