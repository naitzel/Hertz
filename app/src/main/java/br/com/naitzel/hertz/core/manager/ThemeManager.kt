package br.com.naitzel.hertz.core.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import br.com.naitzel.hertz.core.extensions.isTablet
import br.com.naitzel.hertz.presentation.ui.theme.HertzButtonColors
import br.com.naitzel.hertz.presentation.ui.theme.HertzButtonPalettes
import br.com.naitzel.hertz.presentation.ui.theme.HertzColorPalettes
import br.com.naitzel.hertz.presentation.ui.theme.HertzColors
import br.com.naitzel.hertz.presentation.ui.theme.HertzShapePalettes
import br.com.naitzel.hertz.presentation.ui.theme.HertzShapes
import br.com.naitzel.hertz.presentation.ui.theme.HertzSpacing
import br.com.naitzel.hertz.presentation.ui.theme.HertzSpacingPalettes
import br.com.naitzel.hertz.presentation.ui.theme.HertzTypography
import br.com.naitzel.hertz.presentation.ui.theme.HertzTypographyPalettes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(context: Context, preferences: SharedPreferences) {

    val themeName: String = preferences.getString("theme", "HertzTheme") ?: "HertzTheme"

    val isTablet: Boolean = context.isTablet()

    private val _statusBar = MutableStateFlow(false to Color(0xFF2c3e50))

    val statusBar: StateFlow<Pair<Boolean, Color>> = _statusBar.asStateFlow()

    fun getColors(): HertzColors {
        return when (themeName) {
            else -> HertzColorPalettes.Default
        }
    }

    fun getButtonColors(): HertzButtonColors {
        return when (themeName) {
            else -> HertzButtonPalettes.DefaultTheme
        }
    }

    fun getTypography(): HertzTypography {
        return when (isTablet) {
            true -> HertzTypographyPalettes.Default
            false -> HertzTypographyPalettes.Default
        }
    }

    fun getShapes(): HertzShapes {
        return HertzShapePalettes.Default
    }

    fun getSpacing(): HertzSpacing {
        return HertzSpacingPalettes.Default
    }

    fun setStatusBar(light: Boolean, color: Color) {
        _statusBar.value = light to color
    }
}