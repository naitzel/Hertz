package br.com.naitzel.hertz.presentation.ui.screens.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import br.com.naitzel.hertz.core.manager.ThemeManager
import br.com.naitzel.hertz.data.repository.FinancialRepository
import br.com.naitzel.hertz.data.repository.ServiceOrderRepository
import br.com.naitzel.hertz.domain.enums.FinancialStatus
import br.com.naitzel.hertz.domain.enums.ServiceOrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val themeManager: ThemeManager,
    private val serviceOrderRepository: ServiceOrderRepository,
    private val financialRepository: FinancialRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState: StateFlow<HomeUiState> = _uiState

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            delay(2_000)
            _uiState.value = HomeUiState.Empty
        }
    }

    fun onStatusBar(light: Boolean, color: Color) {
        themeManager.setStatusBar(light, color)
    }

    // --------------------------------------------
    // 1️⃣ Estado interno (sem UI)
    // --------------------------------------------

    /** Semana corrente no formato “yyyy‑W” (ex.: 2025‑42) */
    private val _currentWeek = MutableStateFlow(getCurrentWeek())
    val currentWeek: StateFlow<String> get() = _currentWeek

    /** Mês corrente no formato “yyyy‑MM” (ex.: 2025‑10) */
    private val _currentMonth = MutableStateFlow(getCurrentMonth())
    val currentMonth: StateFlow<String> get() = _currentMonth

    /** Filtro de status que a UI pode trocar (ex.: PENDENTE, REALIZADA…) */
    private val _selectedStatus = MutableStateFlow(ServiceOrderStatus.PENDING)
    val selectedStatus: StateFlow<ServiceOrderStatus> get() = _selectedStatus

    // --------------------------------------------
    // 2️⃣ Dados que a UI observa
    // --------------------------------------------

    /** Quantidade de OS por status – atualizada sempre que o filtro muda. */
    val ordersByStatus: Flow<Map<ServiceOrderStatus, Int>> = _selectedStatus
        .flatMapLatest { status ->
            serviceOrderRepository.listOrdersByWeekAndStatus(
                week = _currentWeek.value,
                status = status
            )
        }
        .map { list ->
            // Converte a lista em mapa: status → quantidade
            list.groupingBy { it.status }.eachCount()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

    /** Receita total da semana (soma de `totalValue` das OS) */
    val weeklyRevenue: Flow<Double> = serviceOrderRepository
        .listOrdersByWeek(_currentWeek.value)
        .map { orders -> orders.sumOf { it.totalValue }.toDouble() }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    /** Receita total do mês (opcional – basta mudar a função) */
    val monthlyRevenue: Flow<Double> = serviceOrderRepository
        .listOrdersByMonth(_currentMonth.value)
        .map { orders -> orders.sumOf { it.totalValue }.toDouble() }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    /** Recebíveis pendentes (Financeiro) – opcional */
    val pendingReceivables: Flow<Double> = financialRepository
        .listRecordsByWeekAndStatus(_currentWeek.value, FinancialStatus.PENDING)
        .map { records -> records.sumOf { it.totalValue - it.paidValue }.toDouble() }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    // --------------------------------------------
    // 3️⃣ Métodos públicos (UI chama)
    // --------------------------------------------

    /** Troca o status de filtro */
    fun setSelectedStatus(status: ServiceOrderStatus) {
        _selectedStatus.value = status
    }

    /** Atualiza a semana (ex.: quando o usuário clica em “+1” ou “-1”) */
    fun setWeek(week: String) {
        _currentWeek.value = week
    }

    /** Atualiza o mês (caso queira exibir dados mensais) */
    fun setMonth(month: String) {
        _currentMonth.value = month
    }

    // --------------------------------------------
    // 4️⃣ Helpers de data
    // --------------------------------------------

    /** Retorna a semana corrente no formato “yyyy‑W” */
    private fun getCurrentWeek(): String {
        val now = java.time.LocalDate.now()
        val weekOfYear = now.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        return "${now.year}-$weekOfYear"
    }

    /** Retorna o mês corrente no formato “yyyy‑MM” */
    private fun getCurrentMonth(): String {
        val now = java.time.LocalDate.now()
        return "${now.year}-${now.monthValue.toString().padStart(2, '0')}"
    }
}