package br.com.naitzel.hertz.data.mapper

import br.com.naitzel.hertz.data.local.entity.ServiceOrderEntity
import br.com.naitzel.hertz.domain.enums.ServiceOrderStatus
import br.com.naitzel.hertz.domain.model.ServiceOrderModel
import java.math.BigDecimal
import java.time.Instant
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun ServiceOrderEntity.toDomain(): ServiceOrderModel = ServiceOrderModel(
    id = id,
    customerName = customerName,
    customerPhone = customerPhone,
    customerEmail = customerEmail,
    equipment = equipment,
    equipmentNumber = equipmentNumber,
    equipmentObservation = equipmentObservation,
    dateTime = Instant.ofEpochMilli(dateTime),
    serviceTime = serviceTime,
    description = description,
    laborCost = BigDecimal(laborCost),
    partsCost = BigDecimal(partsCost),
    thirdPartyCost = BigDecimal(thirdPartyCost),
    generalCosts = BigDecimal(generalCosts),
    totalValue = BigDecimal(laborCost + partsCost + thirdPartyCost + generalCosts),
    shouldIssueInvoice = shouldIssueInvoice,
    additionalInfo = additionalInfo,
    status = ServiceOrderStatus.entries.first { it.id == status },
    createdAt = createdAt,
)

@OptIn(ExperimentalTime::class)
fun ServiceOrderModel.toEntity(): ServiceOrderEntity = ServiceOrderEntity(
    id = id,
    customerName = customerName,
    customerPhone = customerPhone,
    customerEmail = customerEmail,
    equipment = equipment,
    equipmentNumber = equipmentNumber,
    equipmentObservation = equipmentObservation,
    dateTime = dateTime.toEpochMilli(),
    serviceTime = serviceTime,
    description = description,
    laborCost = laborCost.toDouble(),
    partsCost = partsCost.toDouble(),
    thirdPartyCost = thirdPartyCost.toDouble(),
    generalCosts = generalCosts.toDouble(),
    shouldIssueInvoice = shouldIssueInvoice,
    additionalInfo = additionalInfo,
    status = status.id,
    createdAt = createdAt,
    updatedAt = System.currentTimeMillis(),
)