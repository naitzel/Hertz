package br.com.naitzel.hertz.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class HertzTypography(
    val title: TextStyle,
    val heading: TextStyle,
    val body: TextStyle,
    val button: TextStyle
)

object HertzTypographyPalettes {
    val Default = HertzTypography(
        title = TextStyle(
            fontSize = 24.sp,
            fontFamily = montserrat,
            fontWeight = FontWeight.Bold
        ),
        heading = TextStyle(
            fontSize = 20.sp,
            fontFamily = montserrat,
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = 16.sp,
            fontFamily = montserrat
        ),
        button = TextStyle(
            fontSize = 14.sp,
            fontFamily = montserrat,
            fontWeight = FontWeight.Normal
        ),
    )
}

val LocalTypography = staticCompositionLocalOf { HertzTypographyPalettes.Default }