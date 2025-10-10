package br.com.naitzel.hertz.core.navigation

import kotlinx.serialization.Serializable

/**
 * Classe de destino de navegação.
 */
@Serializable
sealed class NavigationDestination(val route: String) {
    object Loading : NavigationDestination("loading")
    object Crashed : NavigationDestination("crashed")
    object Home : NavigationDestination("home")
    object ServiceOrderList : NavigationDestination("list")
    object ServiceOrderCreate : NavigationDestination("create")
    object ServiceOrderEdit : NavigationDestination("edit/{id}")
    object Error : NavigationDestination("error")
}