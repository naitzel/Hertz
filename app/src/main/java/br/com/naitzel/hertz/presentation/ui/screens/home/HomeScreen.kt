package br.com.naitzel.hertz.presentation.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.naitzel.hertz.presentation.ui.theme.LocalButtonColors
import br.com.naitzel.hertz.presentation.ui.theme.LocalColors
import br.com.naitzel.hertz.presentation.ui.theme.LocalSpacing
import br.com.naitzel.hertz.presentation.ui.theme.LocalTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onListBudget: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val colors = LocalColors.current
    val btnColors = LocalButtonColors.current
    val typography = LocalTypography.current
    val spacing = LocalSpacing.current

    val ordersStatus by viewModel.ordersByStatus.collectAsState(emptyMap())
    val revenueWeek by viewModel.weeklyRevenue.collectAsState(0.0)
    val pendingByMonth by viewModel.pendingReceivables.collectAsState(0.0)

    BackHandler {

    }

    // Configura a cor da barra de status
    LaunchedEffect(Unit) {
        viewModel.onStatusBar(false, colors.primary)
    }

    // --- Scaffold (TopBar + Content) ------------------------------
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.primary,
                    titleContentColor = colors.onPrimary
                )
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                item {
                    // Bloco Resumo da Semana
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.medium),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = btnColors.card.background)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Resumo da Semana",
                                style = typography.title.copy(
                                    fontSize = 16.sp
                                ),
                                color = btnColors.card.text
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "R$ %.2f".format(revenueWeek),
                                style = typography.body,
                                color = btnColors.card.text
                            )
                        }
                    }

                    // Bloco Ordens por Status (lista animada)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = btnColors.card.background)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                "Ordens por Status",
                                style = typography.title.copy(
                                    fontSize = 16.sp
                                ),
                                color = btnColors.card.text
                            )
                            ordersStatus.forEach { (status, count) ->
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn() + slideInVertically()
                                ) {
                                    Text(
                                        "- ${status.name}: $count",
                                        style = typography.body,
                                        color = btnColors.card.text
                                    )
                                }
                            }
                        }
                    }

                    // Bloco Recebíveis Pendentes do Mês
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = btnColors.card.background)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Recebíveis Pendentes do Mês",
                                style = typography.title.copy(
                                    fontSize = 16.sp
                                ),
                                color = btnColors.card.text
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "R$ %.2f".format(pendingByMonth),
                                style = typography.body,
                                color = btnColors.card.text
                            )
                        }
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .padding(spacing.medium)
                            .fillMaxWidth(),
                        onClick = onListBudget,
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Lista"
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Ir para Lista")
                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onListBudget = {})
}
