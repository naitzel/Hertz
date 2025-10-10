package br.com.naitzel.hertz.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.naitzel.hertz.presentation.ui.theme.LocalColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onListBudget: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val colors = LocalColors.current
    val ordersStatus by viewModel.ordersByStatus.collectAsState(emptyMap())
    val revenueWeek by viewModel.weeklyRevenue.collectAsState(0.0)
    val pendingByMonth by viewModel.pendingReceivables.collectAsState(0.0)

    LaunchedEffect(Unit) {
        viewModel.onStatusBar(false, colors.primary)
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Resumo da Semana", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))

                // Exibe a receita total da semana
                Text("Recebíveis Semanais: R$ %.2f".format(revenueWeek))

                Spacer(Modifier.height(16.dp))
                Text("Ordens por Status:", style = MaterialTheme.typography.bodyLarge)

                // Lista de status e quantidade
                ordersStatus.forEach { (status, count) ->
                    Text("- ${status.name}: $count")
                }

                Spacer(Modifier.height(16.dp))
                Text("Recebíveis Pendentes do Mês: R$ %.2f".format(pendingByMonth))

                Button(onClick = onListBudget) {
                    Text("Ir par Lista")
                }
            }

        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onListBudget = {})
}
