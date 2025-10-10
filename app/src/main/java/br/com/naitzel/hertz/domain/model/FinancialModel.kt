package br.com.naitzel.hertz.domain.model

import br.com.naitzel.hertz.domain.enums.FinancialStatus
import java.math.BigDecimal

class FinancialModel(
    val id: Long = 0,
    val serviceOrderId: Long,
    val dateTime: Long,
    val status: FinancialStatus,
    val totalValue: BigDecimal,
    val paidValue: BigDecimal,
    val paymentDateTime: Long?,
    val additionalInfo: String,
    val installmentNumber: Int = 1,
    val totalInstallments: Int = 1,
    val createdAt: Long = System.currentTimeMillis()
)