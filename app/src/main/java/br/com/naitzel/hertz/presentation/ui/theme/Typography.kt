package br.com.naitzel.hertz.presentation.ui.theme

import androidx.compose.material3.Typography

private val defaultTypography = Typography()

val TypographyCustom = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = montserrat),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = montserrat),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = montserrat),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = montserrat),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = montserrat),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = montserrat),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = montserrat),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = montserrat),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = montserrat),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = montserrat),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = montserrat),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = montserrat),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = montserrat),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = montserrat),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = montserrat),
)