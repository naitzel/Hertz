package br.com.naitzel.hertz.presentation.ui.screens.service_order.list

import androidx.lifecycle.ViewModel
import br.com.naitzel.hertz.data.repository.ServiceOrderRepository
import br.com.naitzel.hertz.domain.enums.ServiceOrderStatus
import br.com.naitzel.hertz.domain.model.ServiceOrderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class OrderListViewModel @Inject constructor(repository: ServiceOrderRepository) : ViewModel() {
    private val _week = MutableStateFlow(getCurrentWeekNumber())
    val week: StateFlow<String> get() = _week

    private val _status = MutableStateFlow(ServiceOrderStatus.PENDING)
    val status: StateFlow<ServiceOrderStatus> get() = _status


    private fun getCurrentWeekNumber(): String {
        val weekFields = WeekFields.of(Locale.getDefault())
        val weekNumber = LocalDate.now().get(weekFields.weekOfYear())
        return weekNumber.toString().padStart(2, '0')
    }
}