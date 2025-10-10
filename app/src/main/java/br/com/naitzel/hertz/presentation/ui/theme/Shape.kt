package br.com.naitzel.hertz.presentation.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

// Shapes
data class HertzShapes(
    val small: Shape,
    val medium: Shape,
    val large: Shape
)

object HertzShapePalettes {
    val Default = HertzShapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(16.dp)
    )
}

val LocalShapes = staticCompositionLocalOf { HertzShapePalettes.Default }
