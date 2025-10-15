package br.com.naitzel.hertz.presentation.ui.screens.service_order.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.naitzel.hertz.data.local.entity.ServiceOrderEntity
import br.com.naitzel.hertz.data.repository.ServiceOrderRepository
import br.com.naitzel.hertz.domain.enums.ServiceOrderStatus
import br.com.naitzel.hertz.domain.model.ServiceOrderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.math.BigDecimal
import java.time.Instant

@HiltViewModel
class ServiceOrderCreateViewModel @Inject constructor(private val repository: ServiceOrderRepository) :
    ViewModel() {

    //  Estado da tela (UI‑state)
    data class UiState(
        val client: String = "",
        val equipment: String = "",
        val dateTime: Long = System.currentTimeMillis(),
        val serviceTime: Int = 0,
        val description: String = "",
        val laborCost: Double = 0.0,
        val partsCost: Double = 0.0,
        val thirdPartyCost: Double = 0.0,
        val generalCost: Double = 0.0,
        val status: ServiceOrderStatus = ServiceOrderStatus.PENDING,
        val emitNF: Boolean = false,
        val notes: String? = null,
        val isInstallment: Boolean = false,
        val installments: Int? = 1
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Resultado da operação (sucesso / erro)
    private val _actionResult = MutableSharedFlow<Result<Long>>() // id retornado
    val actionResult: SharedFlow<Result<Long>> = _actionResult.asSharedFlow()

    /* ----------------- Atualizações de campo --------------- */
    fun onClientChange(text: String)
        = _uiState.update { it.copy(client = text) }
    fun onEquipmentChange(text: String)
        = _uiState.update { it.copy(equipment = text) }
    fun onDateTimeChange(time: Long)
        = _uiState.update { it.copy(dateTime = time) }
    fun onServiceTimeChange(min: Double)
        = _uiState.update { it.copy(serviceTime = min.toInt()) }
    fun onDescriptionChange(text: String)
        = _uiState.update { it.copy(description = text) }
    fun onLaborCostChange(value: Double)
        = _uiState.update { it.copy(laborCost = value) }
    fun onPartsCostChange(value: Double)
        = _uiState.update { it.copy(partsCost = value) }
    fun onThirdPartyCostChange(value: Double)
        = _uiState.update { it.copy(thirdPartyCost = value) }
    fun onGeneralCostChange(value: Double)
        = _uiState.update { it.copy(generalCost = value) }
    fun onStatusChange(status: ServiceOrderStatus)
        = _uiState.update { it.copy(status = status) }
    fun onEmitNFChange(emit: Boolean)
        = _uiState.update { it.copy(emitNF = emit) }
    fun onNotesChange(text: String?)
        = _uiState.update { it.copy(notes = text) }
    fun onIsInstallmentChange(flag: Boolean)
        = _uiState.update { it.copy(isInstallment = flag, installments = if (flag) 1 else null) }
    fun onInstallmentsChange(num: Double)
        = _uiState.update { it.copy(installments = num.toInt()) }

    /* ----------------- Persistência --------------- */
    fun saveOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _uiState.value
            // Cálculo de totalValue (não persiste, apenas mostra)
            val total = state.laborCost + state.partsCost +
                    state.thirdPartyCost + state.generalCost

            val order = ServiceOrderModel(
                id = 0,
                customerName = "",
                customerPhone = "",
                customerEmail = "",
                equipment = "",
                equipmentNumber = "",
                equipmentObservation = "",
                dateTime = Instant.now(),
                serviceTime = "",
                description = "",
                laborCost = BigDecimal(0),
                partsCost = BigDecimal(0),
                thirdPartyCost = BigDecimal(0),
                generalCosts = BigDecimal(0),
                totalValue = BigDecimal(0),
                shouldIssueInvoice = false,
                additionalInfo = "",
            )

            try {
                val id = repository.insert(order)
                _actionResult.emit(Result.success(id))
            } catch (e: Exception) {
                _actionResult.emit(Result.failure(e))
            }
        }
    }

    fun loadOrder(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = repository.get(id)
            if (order != null) {
                _uiState.value = UiState(
//                    client = order.client,
//                    equipment = order.equipment,
//                    dateTime = order.dateTime,
//                    serviceTime = order.serviceTime,
//                    description = order.description,
//                    laborCost = order.laborCost,
//                    partsCost = order.partsCost,
//                    thirdPartyCost = order.thirdPartyCost,
//                    generalCost = order.generalCost,
//                    status = order.status,
//                    emitNF = order.emitNF,
//                    notes = order.notes,
//                    isInstallment = order.isInstallment,
//                    installments = order.installments
                )
            }
        }
    }

    fun updateOrder(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _uiState.value
            val total = state.laborCost + state.partsCost +
                    state.thirdPartyCost + state.generalCost

//            val order = ServiceOrder(
//                id = id,
//                client = state.client,
//                equipment = state.equipment,
//                dateTime = state.dateTime,
//                serviceTime = state.serviceTime,
//                description = state.description,
//                laborCost = state.laborCost,
//                partsCost = state.partsCost,
//                thirdPartyCost = state.thirdPartyCost,
//                generalCost = state.generalCost,
//                status = state.status,
//                emitNF = state.emitNF,
//                notes = state.notes,
//                isInstallment = state.isInstallment,
//                installments = state.installments
//            ).apply { totalValue = total }

            try {
//                repository.update(order)
                _actionResult.emit(Result.success(id))
            } catch (e: Exception) {
                _actionResult.emit(Result.failure(e))
            }
        }
    }

    /* ----------------- Exporte de Excel (simplificado) --------------- */
    fun exportOrdersToExcel(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
//            val orders = repository.listByWeek(YearWeek.now()).first() // pega a semana atual
//            val workbook = XSSFWorkbook()
//            val sheet = workbook.createSheet("Ordens")
//            // Cabeçalho
//            val headerRow = sheet.createRow(0)
//            listOf(
//                "ID", "Cliente", "Equipamento", "Data/Hora",
//                "Tempo (min)", "Descrição", "Mão de obra",
//                "Peças", "Terceiros", "Custos Gerais", "Total", "Status",
//                "Emitir NF", "Notas", "Parcelado", "Qtde Parc."
//            ).forEachIndexed { idx, title ->
//                headerRow.createCell(idx).setCellValue(title)
//            }
//
//            orders.forEachIndexed { i, o ->
//                val row = sheet.createRow(i + 1)
//                listOf(
//                    o.id.toDouble(), o.client, o.equipment,
//                    LocalDateTime.ofInstant(Instant.ofEpochMilli(o.dateTime), ZoneId.systemDefault()).toString(),
//                    o.serviceTime.toDouble(), o.description,
//                    o.laborCost, o.partsCost, o.thirdPartyCost,
//                    o.generalCost, o.totalValue, o.status.name,
//                    if (o.emitNF) 1.0 else 0.0, o.notes ?: "",
//                    if (o.isInstallment) 1.0 else 0.0, o.installments?.toDouble() ?: 0.0
//                ).forEachIndexed { idx, value ->
//                    row.createCell(idx).setCellValue(value)
//                }
//            }
//
//            workbook.write(file.outputStream())
//            workbook.close()
        }
    }
}