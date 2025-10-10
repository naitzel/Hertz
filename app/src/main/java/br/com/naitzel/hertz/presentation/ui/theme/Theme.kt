package br.com.naitzel.hertz.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import br.com.naitzel.hertz.core.manager.ThemeManager

@Composable
fun HertzTheme(
    themeManager: ThemeManager,
    content: @Composable (() -> Unit)
) {
    CompositionLocalProvider(
        LocalColors provides themeManager.getColors(),
        LocalButtonColors provides themeManager.getButtonColors(),
        LocalTypography provides themeManager.getTypography(),
        LocalShapes provides themeManager.getShapes(),
        LocalSpacing provides themeManager.getSpacing(),
    ) {
        content()
    }
}