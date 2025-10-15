package br.com.naitzel.hertz.presentation.ui.screens.service_order.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.naitzel.hertz.presentation.ui.theme.LocalColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(
    onHome: () -> Unit,
    onNewServiceOrder: () -> Unit,
    onEditServiceOrder: (String) -> Unit,
    viewModel: OrderListViewModel = hiltViewModel()
) {
    val colors = LocalColors.current

//    val budgets by viewModel.allBudgets.observeAsState(emptyList())
//    var showDeleteDialog by remember { mutableStateOf<String?>(null) }
//    var filterOption by remember { mutableIntStateOf(0) }
//    val dateFormat = remember { SimpleDateFormat("MM/dd/yyyy", Locale.US) }
//    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.US) }
//
//    val filteredBudgets = when (filterOption) {
//        1 -> budgets.filter { !it.isCompleted }
//        2 -> budgets.filter { !it.isPaid && it.isCompleted }
//        3 -> budgets.filter { it.isCompleted }
//        else -> budgets
//    }
//

    BackHandler {
        onHome()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ordens de Serviço") },
                actions = {
                    IconButton(onClick = onNewServiceOrder) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Nova Ordem",
                            tint = colors.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.primary,
                    titleContentColor = colors.onPrimary
                )
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
//            // Filter tabs
//            ScrollableTabRow(
//                selectedTabIndex = filterOption,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Tab(
//                    selected = filterOption == 0,
//                    onClick = { filterOption = 0 },
//                    text = { Text("All (${budgets.size})") }
//                )
//                Tab(
//                    selected = filterOption == 1,
//                    onClick = { filterOption = 1 },
//                    text = { Text("Pending (${budgets.count { !it.isCompleted }})") }
//                )
//                Tab(
//                    selected = filterOption == 2,
//                    onClick = { filterOption = 2 },
//                    text = { Text("Unpaid (${budgets.count { !it.isPaid && it.isCompleted }})") }
//                )
//                Tab(
//                    selected = filterOption == 3,
//                    onClick = { filterOption = 3 },
//                    text = { Text("Completed (${budgets.count { it.isCompleted }})") }
//                )
//            }
//
//            if (filteredBudgets.isEmpty()) {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            Icons.Default.Description,
//                            contentDescription = null,
//                            modifier = Modifier.size(64.dp),
//                            tint = MaterialTheme.colorScheme.outline
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Text(
//                            "No budgets found",
//                            style = MaterialTheme.typography.bodyLarge,
//                            color = MaterialTheme.colorScheme.outline
//                        )
//                    }
//                }
//            } else {
//                LazyColumn(
//                    contentPadding = PaddingValues(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(filteredBudgets) { budget ->
//                        ServiceOrderCard(
//                            budget = budget,
//                            dateFormat = dateFormat,
//                            currencyFormat = currencyFormat,
//                            onEdit = { onEditServiceOrder(budget.id) },
//                            onDelete = { showDeleteDialog = budget.id }
//                        )
//                    }
//                }
//            }
        }
//
//        if (showDeleteDialog != null) {
//            AlertDialog(
//                onDismissRequest = { showDeleteDialog = null },
//                title = { Text("Delete Budget") },
//                text = { Text("Are you sure you want to delete this budget?") },
//                confirmButton = {
//                    TextButton(
//                        onClick = {
//                            viewModel.deleteBudget(showDeleteDialog!!)
//                            showDeleteDialog = null
//                        }
//                    ) {
//                        Text("Delete", color = Color.Red)
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = { showDeleteDialog = null }) {
//                        Text("Cancel")
//                    }
//                }
//            )
//        }
    }
}
