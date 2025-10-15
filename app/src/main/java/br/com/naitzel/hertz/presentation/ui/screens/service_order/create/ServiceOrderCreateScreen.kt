package br.com.naitzel.hertz.presentation.ui.screens.service_order.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.naitzel.hertz.domain.enums.ServiceOrderStatus
import br.com.naitzel.hertz.presentation.ui.theme.LocalButtonColors
import br.com.naitzel.hertz.presentation.ui.theme.LocalColors
import br.com.naitzel.hertz.presentation.ui.theme.LocalSpacing
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceOrderCreateScreen(
    orderId: Long? = null,
    onNavigateBack: () -> Unit,
    viewModel: ServiceOrderCreateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val colors = LocalColors.current
    val spacing = LocalSpacing.current
    val scaffoldState = rememberScrollState()

    BackHandler {
        onNavigateBack()
    }

    // Carrega a ordem quando existe ID
    LaunchedEffect(orderId) {
        orderId?.let { viewModel.loadOrder(it) }
    }

    // Escuta resultado da operação
    LaunchedEffect(Unit) {
        viewModel.actionResult.collect { result ->
            result.onSuccess { id ->
//                scaffoldState.snackbarHostState.showSnackbar(
//                    if (orderId == null) "Ordem cadastrada: $id"
//                    else "Ordem atualizada: $id"
//                )
//                onNavigateBack()
            }
            result.onFailure { e ->
//                scaffoldState.snackbarHostState.showSnackbar("Erro: ${e.localizedMessage}")
            }
        }
    }

    Scaffold(
//        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(if (orderId == null) "Nova OS" else "Editar OS") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.primary,
                    titleContentColor = colors.onPrimary
                )
            )
        },

//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                if (orderId == null)
//                    viewModel.saveOrder()
//                else
//                    viewModel.updateOrder(orderId)
//            }) {
//                Icon(Icons.Default.Save, contentDescription = "Salvar")
//            }
//        },
        modifier = Modifier.imePadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(spacing.medium)
        ) {
            // 1. Cliente
            OutlinedTextField(
                value = uiState.client,
                onValueChange = viewModel::onClientChange,
                label = { Text("Cliente") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // 2. Equipamento
            OutlinedTextField(
                value = uiState.equipment,
                onValueChange = viewModel::onEquipmentChange,
                label = { Text("Equipamento") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // 3. Data/Hora – usa DatePicker + TimePicker
            DateTimePicker(
                dateMillis = uiState.dateTime,
                onDateChange = { new -> viewModel.onDateTimeChange(new) }
            )
            Spacer(Modifier.height(8.dp))

            NumberInput(
                value = uiState.serviceTime.toDouble(),
                onValueChange = viewModel::onServiceTimeChange,
                label = "Tempo de serviço (min)"
            )
            Spacer(Modifier.height(8.dp))

            // 5. Descrição
            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NumberInput(
                    value = uiState.laborCost,
                    onValueChange = viewModel::onLaborCostChange,
                    label = "Mão de obra",
                    modifier = Modifier.weight(1f)
                )
                NumberInput(
                    value = uiState.partsCost,
                    onValueChange = viewModel::onPartsCostChange,
                    label = "Peças",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NumberInput(
                    value = uiState.thirdPartyCost,
                    onValueChange = viewModel::onThirdPartyCostChange,
                    label = "Terceiros",
                    modifier = Modifier.weight(1f)
                )
                NumberInput(
                    value = uiState.generalCost,
                    onValueChange = viewModel::onGeneralCostChange,
                    label = "Custos Gerais",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(8.dp))

            // 7. Status – dropdown
            DropdownMenuField(
                options = ServiceOrderStatus.entries,
                selected = uiState.status,
                onSelect = viewModel::onStatusChange,
                label = "Status"
            )
            Spacer(Modifier.height(8.dp))

            // 8. Emitir NF – checkbox
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.emitNF,
                    onCheckedChange = viewModel::onEmitNFChange
                )
                Text("Emitir Nota Fiscal")
            }
            Spacer(Modifier.height(8.dp))

            // 9. Informações adicionais
            OutlinedTextField(
                value = uiState.notes ?: "",
                onValueChange = viewModel::onNotesChange,
                label = { Text("Informações adicionais") },
                minLines = 3,
                maxLines = 6,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // 10. Recebíveis – parcelado ou não
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.isInstallment,
                    onCheckedChange = viewModel::onIsInstallmentChange
                )
                Text("Gerar cobrança parcelada")
            }
            if (uiState.isInstallment) {
                NumberInput(
                    value = (uiState.installments ?: 1).toDouble(),
                    onValueChange = viewModel::onInstallmentsChange,
                    label = "Número de parcelas",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(18.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Lista"
                )
                Spacer(Modifier.width(8.dp))
                Text("Salvar")
            }
        }
    }
}

@Composable
fun NumberInput(
    value: Double,
    onValueChange: (Double) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = { text ->
            val v = text
                .replace(",", ".")
                .toDoubleOrNull() ?: 0.0
            onValueChange(v)
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Composable
fun DropdownMenuField(
    options: List<ServiceOrderStatus>,
    selected: ServiceOrderStatus,
    onSelect: (ServiceOrderStatus) -> Unit,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selected.description,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown, contentDescription = null,
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(
                    onClick = {
                        onSelect(opt)
                        expanded = false
                    },
                    text = { Text(opt.description) }
                )
            }
        }
    }
}

@Composable
fun DateTimePicker(dateMillis: Long, onDateChange: (Long) -> Unit) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance().apply { timeInMillis = dateMillis }

    OutlinedTextField(
        value = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            .format(
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(dateMillis),
                    ZoneId.systemDefault()
                )
            ),
        onValueChange = {},
        readOnly = true,
        label = { Text("Data e Hora") },
        trailingIcon = {
            Icon(
                Icons.Default.CalendarToday, contentDescription = null,
                modifier = Modifier.clickable { showDialog = true })
        },
        modifier = Modifier.fillMaxWidth()
    )

    if (showDialog) {
        // Combina DatePicker + TimePicker
        val picker = DatePickerDialog(
            context,
            { _, year, month, day ->
                calendar.set(year, month, day)
                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        onDateChange(calendar.timeInMillis)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }
}

//    var isLoading by remember { mutableStateOf(budgetId != null) }
//
//    var customerName by remember { mutableStateOf("") }
//    var customerPhone by remember { mutableStateOf("") }
//    var customerEmail by remember { mutableStateOf("") }
//    var device by remember { mutableStateOf("") }
//    var serialNumber by remember { mutableStateOf("") }
//    var estimatedTime by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var laborCost by remember { mutableStateOf("") }
//    var partsCost by remember { mutableStateOf("") }
//    var thirdPartyCost by remember { mutableStateOf("") }
//    var generalCosts by remember { mutableStateOf("") }
//    var isPaid by remember { mutableStateOf(false) }
//    var hasInvoice by remember { mutableStateOf(false) }
//    var isCompleted by remember { mutableStateOf(false) }
//    var paymentMethod by remember { mutableStateOf("") }
//    var notes by remember { mutableStateOf("") }
//    var showError by remember { mutableStateOf(false) }
//    var originalBudget by remember { mutableStateOf<ServiceOrderEntity?>(null) }
//
//    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.US) }
//
//    val totalCost = remember(laborCost, partsCost, thirdPartyCost, generalCosts) {
//        val labor = laborCost.toDoubleOrNull() ?: 0.0
//        val parts = partsCost.toDoubleOrNull() ?: 0.0
//        val third = thirdPartyCost.toDoubleOrNull() ?: 0.0
//        val general = generalCosts.toDoubleOrNull() ?: 0.0
//        labor + parts + third + general
//    }
//
//    LaunchedEffect(budgetId) {
//        if (budgetId != null) {
//            val loadedBudget = viewModel.getBudgetById(budgetId)
//            if (loadedBudget != null) {
//                originalBudget = loadedBudget.copy()
//                customerName = loadedBudget.customerName
//                customerPhone = loadedBudget.customerPhone
//                customerEmail = loadedBudget.customerEmail
//                device = loadedBudget.device
//                serialNumber = loadedBudget.serialNumber
//                estimatedTime = loadedBudget.estimatedTime
//                description = loadedBudget.description
//                laborCost = loadedBudget.laborCost.toString()
//                partsCost = loadedBudget.partsCost.toString()
//                thirdPartyCost = loadedBudget.thirdPartyCost.toString()
//                generalCosts = loadedBudget.generalCosts.toString()
//                isPaid = loadedBudget.isPaid
//                hasInvoice = loadedBudget.hasInvoice
//                isCompleted = loadedBudget.isCompleted
//                paymentMethod = loadedBudget.paymentMethod
//                notes = loadedBudget.notes
//            }
//            isLoading = false
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(if (budgetId != null) "Edit Budget" else "New Budget") },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Default.ArrowBack, "Back")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer
//                )
//            )
//        }
//    ) { padding ->
//        if (isLoading) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(padding),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        } else {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(padding)
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                // Customer Info Section
//                item {
//                    Text(
//                        "Customer Information",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = customerName,
//                        onValueChange = { customerName = it },
//                        label = { Text("Customer Name *") },
//                        leadingIcon = { Icon(Icons.Default.Person, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        isError = showError && customerName.isBlank()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = customerPhone,
//                        onValueChange = { customerPhone = it },
//                        label = { Text("Phone") },
//                        leadingIcon = { Icon(Icons.Default.Phone, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = customerEmail,
//                        onValueChange = { customerEmail = it },
//                        label = { Text("Email") },
//                        leadingIcon = { Icon(Icons.Default.Email, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
//                    )
//                }
//
//                // Device Info Section
//                item {
//                    Text(
//                        "Device Information",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = device,
//                        onValueChange = { device = it },
//                        label = { Text("Device *") },
//                        leadingIcon = { Icon(Icons.Default.Devices, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        isError = showError && device.isBlank()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = serialNumber,
//                        onValueChange = { serialNumber = it },
//                        label = { Text("Serial Number") },
//                        leadingIcon = { Icon(Icons.Default.Tag, null) },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = estimatedTime,
//                        onValueChange = { estimatedTime = it },
//                        label = { Text("Estimated Time *") },
//                        placeholder = { Text("e.g., 2 days, 5 hours") },
//                        leadingIcon = { Icon(Icons.Default.Schedule, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        isError = showError && estimatedTime.isBlank()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = description,
//                        onValueChange = { description = it },
//                        label = { Text("Description *") },
//                        leadingIcon = { Icon(Icons.Default.Description, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        minLines = 3,
//                        isError = showError && description.isBlank()
//                    )
//                }
//
//                // Cost Breakdown Section
//                item {
//                    Text(
//                        "Cost Breakdown",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = laborCost,
//                        onValueChange = { laborCost = it.filter { c -> c.isDigit() || c == '.' } },
//                        label = { Text("Labor Cost ($) *") },
//                        leadingIcon = { Icon(Icons.Default.Build, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                        isError = showError && laborCost.isBlank()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = partsCost,
//                        onValueChange = { partsCost = it.filter { c -> c.isDigit() || c == '.' } },
//                        label = { Text("Parts Cost ($)") },
//                        leadingIcon = { Icon(Icons.Default.Category, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = thirdPartyCost,
//                        onValueChange = {
//                            thirdPartyCost = it.filter { c -> c.isDigit() || c == '.' }
//                        },
//                        label = { Text("Third Party Cost ($)") },
//                        leadingIcon = { Icon(Icons.Default.Group, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = generalCosts,
//                        onValueChange = {
//                            generalCosts = it.filter { c -> c.isDigit() || c == '.' }
//                        },
//                        label = { Text("General Costs ($)") },
//                        leadingIcon = { Icon(Icons.Default.Money, null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
//                    )
//                }
//
//                // Total Display
//                item {
//                    Card(
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = CardDefaults.cardColors(
//                            containerColor = MaterialTheme.colorScheme.primaryContainer
//                        )
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                "Total Cost",
//                                style = MaterialTheme.typography.titleLarge,
//                                fontWeight = FontWeight.Bold
//                            )
//                            Text(
//                                currencyFormat.format(totalCost),
//                                style = MaterialTheme.typography.titleLarge,
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colorScheme.primary
//                            )
//                        }
//                    }
//                }
//
//                // Status Checks Section
//                item {
//                    Text(
//                        "Status",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//
//                item {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF66BB6A))
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Text("Paid")
//                        }
//                        Switch(checked = isPaid, onCheckedChange = { isPaid = it })
//                    }
//                }
//            }
//        }
//    }

