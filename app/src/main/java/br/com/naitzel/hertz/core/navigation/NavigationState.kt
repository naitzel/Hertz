package br.com.naitzel.hertz.core.navigation

sealed class NavigationState {
    object Loading : NavigationState()

    object Crashed : NavigationState()

    object Home : NavigationState()

    object ServiceOrderList : NavigationState()

    object ServiceOrderCreate : NavigationState()

    object ServiceOrderEdit : NavigationState()

    data class Error(
        val message: String,
        val canRetry: Boolean = true
    ) : NavigationState()
}