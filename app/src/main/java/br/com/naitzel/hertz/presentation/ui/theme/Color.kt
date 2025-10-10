package br.com.naitzel.hertz.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class HertzColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val error: Color,
    val onError: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color
)

object HertzColorPalettes {
    val Default = HertzColors(
        primary = Color(0xFFc0392b),
        onPrimary = Color.White,
        secondary = Color(0xFF27ae60),
        onSecondary = Color.White,
        tertiary = Color(0xFF2980b9),
        onTertiary = Color.White,
        error = Color(0xFFc0392b),
        onError = Color.White,
        background = Color(0xFFecf0f1),
        onBackground = Color.Black,
        surface = Color(0xFF005c6c),
        onSurface = Color(0xFFFFFFFF),
    )
}

data class HertzButtonColor(
    val text: Color,
    val background: Color
)

data class HertzButtonColors(
    val primary: HertzButtonColor,
    val secondary: HertzButtonColor,
    val danger: HertzButtonColor
)


object HertzButtonPalettes {
    val DefaultTheme = HertzButtonColors(
        primary = HertzButtonColor(
            text = Color(0xFFFFFFFF),
            background = Color(0xFF135d6d)
        ),
        secondary = HertzButtonColor(
            text = Color(0xFFecf0f1),
            background = Color(0xFF1f2e58)
        ),
        danger = HertzButtonColor(
            text = Color(0xFFFFFFFF),
            background = Color(0xFFc0392b)
        )
    )
}

val LocalColors = staticCompositionLocalOf { HertzColorPalettes.Default }
val LocalButtonColors = staticCompositionLocalOf { HertzButtonPalettes.DefaultTheme }