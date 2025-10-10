package br.com.naitzel.hertz.core.navigation

sealed class NavigationEffect {
    object ClearBackStack : NavigationEffect()
    data class PopUpTo(val route: String, val inclusive: Boolean = false) : NavigationEffect()
    data class ShowSnackBar(val message: String) : NavigationEffect()
}