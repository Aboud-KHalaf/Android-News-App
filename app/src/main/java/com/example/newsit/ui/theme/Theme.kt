package com.example.newsit.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Orange40,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Orange90,
    onPrimaryContainer = Orange10,
    secondary = DarkOrangeGray40,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = DarkOrangeGray90,
    onSecondaryContainer = DarkOrangeGray10,
    tertiary = OrangeBlue40,
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = OrangeBlue90,
    onTertiaryContainer = OrangeBlue10,
    error = Red40,
    onError = Color(0xFFFFFFFF),
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Neutral98,
    onBackground = Neutral10,
    surface = Neutral98,
    onSurface = Neutral10,
    surfaceVariant = NeutralVar90,
    onSurfaceVariant = NeutralVar30,
    outline = NeutralVar50,
    outlineVariant = NeutralVar80,
    inverseSurface = Neutral20,
    inverseOnSurface = Neutral95,
    inversePrimary = Orange80,
    surfaceTint = Orange40
)

private val DarkColorScheme = darkColorScheme(
    primary = Orange80,
    onPrimary = Orange20,
    primaryContainer = Orange30,
    onPrimaryContainer = Orange90,
    secondary = DarkOrangeGray80,
    onSecondary = DarkOrangeGray20,
    secondaryContainer = DarkOrangeGray30,
    onSecondaryContainer = DarkOrangeGray90,
    tertiary = OrangeBlue80,
    onTertiary = OrangeBlue20,
    tertiaryContainer = OrangeBlue30,
    onTertiaryContainer = OrangeBlue90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Neutral6,
    onBackground = Neutral90,
    surface = Neutral6,
    onSurface = Neutral90,
    surfaceVariant = NeutralVar30,
    onSurfaceVariant = NeutralVar80,
    outline = NeutralVar60,
    outlineVariant = NeutralVar30,
    inverseSurface = Neutral90,
    inverseOnSurface = Neutral20,
    inversePrimary = Orange40,
    surfaceTint = Orange80
)

@Composable
fun NewsItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
