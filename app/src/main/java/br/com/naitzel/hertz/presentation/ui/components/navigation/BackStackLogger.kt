package br.com.naitzel.hertz.presentation.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import timber.log.Timber

/**
 * Observa a navegação e mantém histórico de rotas.
 * Log cada mudança de rota usando Timber com a tag fornecida.
 */
@Composable
fun BackStackLogger(navController: NavHostController, tag: String = "BackStack") {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    /**
     * Log da rota atual usando Timber com a tag fornecida.
     */
    LaunchedEffect(navBackStackEntry) {
        val currentRoute = navBackStackEntry?.destination?.route
        Timber.tag(tag).d("Rota atual: %s", currentRoute)
    }
}