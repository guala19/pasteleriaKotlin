package com.example.pasteleriakotlin.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pasteleriakotlin.R

// Font families
val PacificoFont = FontFamily(Font(R.font.pacifico_regular))
val LatoFont = FontFamily(
    Font(R.font.lato_regular),
    Font(R.font.lato_bold, FontWeight.Bold)
)

val PasteleriaTypography = Typography(
    // Títulos grandes en Pacifico (manuscrita)
    headlineLarge = TextStyle(
        fontFamily = PacificoFont,
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 44.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = PacificoFont,
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 40.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = PacificoFont,
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 36.sp
    ),
    // Títulos medianos en Lato Bold
    titleLarge = TextStyle(
        fontFamily = LatoFont,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = LatoFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = LatoFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp
    ),
    // Cuerpo de texto en Lato
    bodyLarge = TextStyle(
        fontFamily = LatoFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = LatoFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = LatoFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),
    // Etiquetas
    labelLarge = TextStyle(
        fontFamily = LatoFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = LatoFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = LatoFont,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp
    )
)
