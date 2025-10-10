package br.com.naitzel.hertz.presentation.ui.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.naitzel.hertz.core.navigation.NavigationDestination
import br.com.naitzel.hertz.core.viewModels.NavigationViewModel
import br.com.naitzel.hertz.presentation.ui.components.LoadingComponent
import br.com.naitzel.hertz.presentation.ui.screens.service_order.create.ServiceOrderCreateScreen
import br.com.naitzel.hertz.presentation.ui.screens.service_order.list.OrderListScreen
import br.com.naitzel.hertz.presentation.ui.screens.crashed.CrashedScreen
import br.com.naitzel.hertz.presentation.ui.screens.home.HomeScreen

/**
 * NavHost principal da aplicação
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = NavigationDestination.Loading.route,
    viewModel: NavigationViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Loading Screen
        composable(NavigationDestination.Loading.route) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingComponent(message = "Carregando dados...")
            }
        }

        // Error Screen
        composable(NavigationDestination.Crashed.route) {
            CrashedScreen {
                // Verificar processos de inicialização
                viewModel.onInitialize()
            }
        }

        // Main Screen
        composable(NavigationDestination.Home.route) {
            HomeScreen(
                onListBudget = {
                    viewModel.navigateToList()
                }
            )
        }

        // Main Screen
        composable(NavigationDestination.ServiceOrderList.route) {
            OrderListScreen(
                onNewServiceOrder = {
                    viewModel.navigateToCreate()
                },
                onEditServiceOrder = {
                    viewModel.navigateToEdit()
                },
            )
        }

        composable(NavigationDestination.ServiceOrderCreate.route) {
            ServiceOrderCreateScreen(
                onNavigateBack = {}
            )
        }

        composable(NavigationDestination.ServiceOrderEdit.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                ServiceOrderCreateScreen(
                    orderId = id.toLong(),
                    onNavigateBack = {}
                )
            }
        }
    }
}