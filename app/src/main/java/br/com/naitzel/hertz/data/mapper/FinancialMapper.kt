package br.com.naitzel.hertz.data.mapper

import br.com.naitzel.hertz.data.local.entity.FinancialEntity
import br.com.naitzel.hertz.domain.enums.FinancialStatus
import br.com.naitzel.hertz.domain.model.FinancialModel
import java.math.BigDecimal
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun FinancialEntity.toDomain(): FinancialModel = FinancialModel(
    id = id,
    serviceOrderId = serviceOrderId,
    dateTime = dateTime,
    status = FinancialStatus.entries.first { it.id == status },
    totalValue = BigDecimal(totalValue),
    paidValue = BigDecimal(paidValue),
    paymentDateTime = paymentDateTime,
    additionalInfo = additionalInfo,
    installmentNumber = installmentNumber,
    totalInstallments = totalInstallments
)

@OptIn(ExperimentalTime::class)
fun FinancialModel.toEntity(): FinancialEntity = FinancialEntity(
    id = id,
    serviceOrderId = serviceOrderId,
    dateTime = dateTime,
    status = status.id,
    totalValue = totalValue.toDouble(),
    paidValue = paidValue.toDouble(),
    paymentDateTime = paymentDateTime,
    additionalInfo = additionalInfo,
    installmentNumber = installmentNumber,
    totalInstallments = totalInstallments,
    createdAt = createdAt
)