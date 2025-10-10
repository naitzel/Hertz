package br.com.naitzel.hertz.presentation.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.naitzel.hertz.core.navigation.NavigationDestination
import br.com.naitzel.hertz.core.navigation.NavigationEffect
import br.com.naitzel.hertz.core.navigation.NavigationState
import br.com.naitzel.hertz.core.viewModels.NavigationViewModel
import timber.log.Timber

/**
 * Observa o estado de navegação e navega conforme necessário.
 */
@Composable
fun NavigationObserver(
    navController: NavHostController,
    tag: String = "NavigationObserver",
    viewModel: NavigationViewModel = hiltViewModel()
) {
    val navigationState by viewModel.navigationState.collectAsState()

    // Observar efeitos de navegação
    LaunchedEffect(viewModel) {
        viewModel.navigationEffect.collect { effect ->
            when (effect) {
                is NavigationEffect.ClearBackStack -> {
                    navController.navigate(getCurrentRoute(navigationState).route) {
                        popUpTo(0) { inclusive = true }
                    }
                }

                is NavigationEffect.PopUpTo -> {
                    navController.popBackStack(effect.route, effect.inclusive)
                }

                is NavigationEffect.ShowSnackBar -> {
                    // Mostrar snackBar
                }
            }
        }
    }

    // Observar mudanças de estado para navegar
    LaunchedEffect(navigationState) {
        val route = getCurrentRoute(navigationState)
        val currentRoute = navController.currentDestination?.route

        if (route.route != currentRoute) {
            Timber.tag(tag).d("Estado de navegação: %s", navigationState)

            navController.navigate(route.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
}

/**
 * Obtém a rota atual com base no estado de navegação.
 */
private fun getCurrentRoute(state: NavigationState): NavigationDestination {
    return when (state) {
        is NavigationState.Loading -> NavigationDestination.Loading
        is NavigationState.Home -> NavigationDestination.Home
        is NavigationState.Crashed -> NavigationDestination.Crashed
        is NavigationState.Error -> NavigationDestination.Error
        is NavigationState.ServiceOrderList -> NavigationDestination.ServiceOrderList
        is NavigationState.ServiceOrderCreate -> NavigationDestination.ServiceOrderCreate
        is NavigationState.ServiceOrderEdit -> NavigationDestination.ServiceOrderEdit
    }
}