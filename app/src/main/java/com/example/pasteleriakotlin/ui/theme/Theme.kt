package com.example.pasteleriakotlin.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Colores Mil Sabores
private val CreamLight = Color(0xFFFFE8D1)
private val Cream = Color(0xFFFFF5E1)
private val Chocolate = Color(0xFF8B4513)
private val ChocolateDark = Color(0xFF5D4037)
private val Rose = Color(0xFFFFC0CB)
private val GraySecondary = Color(0xFFB0BEC5)

private val LightColors = lightColorScheme(
    primary = Chocolate,
    secondary = Rose,
    tertiary = CreamLight,
    background = Cream,
    surface = Cream,
    onBackground = ChocolateDark,
    onSurface = ChocolateDark
)

private val DarkColors = darkColorScheme(
    primary = Chocolate,
    secondary = Rose,
    tertiary = CreamLight,
    background = ChocolateDark,
    surface = ChocolateDark,
    onBackground = Cream,
    onSurface = Cream
)

// Tema basico con soporte para colores dinamicos y tipografía personalizada
@Composable
fun PasteleriaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = PasteleriaTypography,
        content = content
    )
}
