package br.com.naitzel.hertz.core.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import io.konform.validation.ValidationError
import kotlin.collections.associate
import kotlin.to

fun List<ValidationError>?.toMap(): Map<String, String> {
    return this?.associate { it.dataPath to it.message } ?: emptyMap()
}

@SuppressLint("ObsoleteSdkInt")
fun Context.isTablet(): Boolean {
    val configuration = resources.configuration

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
        // API 13+ usa smallestScreenWidthDp
        configuration.smallestScreenWidthDp >= 600
    } else {
        // APIs antigas: fallback usando largura em px / densidade
        val metrics = resources.displayMetrics
        val widthDp = metrics.widthPixels / metrics.density
        widthDp >= 600
    }
}