package br.com.naitzel.hertz.domain.model

import br.com.naitzel.hertz.domain.enums.ServiceOrderStatus
import java.math.BigDecimal
import java.time.Instant

class ServiceOrderModel(
    val id: Long = 0,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val equipment: String,
    val equipmentNumber: String,
    val equipmentObservation: String,
    val dateTime: Instant,
    val serviceTime: String,
    val description: String,
    val laborCost: BigDecimal,
    val partsCost: BigDecimal,
    val thirdPartyCost: BigDecimal,
    val generalCosts: BigDecimal,
    val totalValue: BigDecimal,
    val shouldIssueInvoice: Boolean,
    val additionalInfo: String = "",
    val status: ServiceOrderStatus = ServiceOrderStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
)