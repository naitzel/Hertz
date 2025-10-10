package br.com.naitzel.hertz.data.repository

import br.com.naitzel.hertz.data.local.dao.FinancialDao
import br.com.naitzel.hertz.data.mapper.toDomain
import br.com.naitzel.hertz.data.mapper.toEntity
import br.com.naitzel.hertz.domain.enums.FinancialStatus
import br.com.naitzel.hertz.domain.model.FinancialModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FinancialRepository(private val dao: FinancialDao) {
    suspend fun getAll(): List<FinancialModel> =
        dao.getAll().map { it.toDomain() }

    suspend fun insert(order: FinancialModel) =
        dao.insert(order.toEntity())

    suspend fun update(order: FinancialModel) =
        dao.update(order.toEntity())

    suspend fun delete(order: FinancialModel) =
        dao.delete(order.toEntity())

    fun listRecordsByWeekAndStatus(
        value: String,
        pendente: FinancialStatus
    ): Flow<List<FinancialModel>> =
        dao.getRecordsByWeekAndStatus(value, pendente.id)
            .map { list -> list.map { it.toDomain() } }
}