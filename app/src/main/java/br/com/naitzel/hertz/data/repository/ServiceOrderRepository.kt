package br.com.naitzel.hertz.data.repository

import br.com.naitzel.hertz.data.local.dao.ServiceOrderDao
import br.com.naitzel.hertz.data.local.entity.ServiceOrderEntity
import br.com.naitzel.hertz.data.mapper.toDomain
import br.com.naitzel.hertz.data.mapper.toEntity
import br.com.naitzel.hertz.domain.enums.ServiceOrderStatus
import br.com.naitzel.hertz.domain.model.ServiceOrderModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServiceOrderRepository(private val dao: ServiceOrderDao) {
    suspend fun getAll(): List<ServiceOrderModel> =
        dao.getAll().map { it.toDomain() }

    suspend fun get(id: Long) = dao.getById(id)

    suspend fun insert(order: ServiceOrderModel) : Long =
        dao.insert(order.toEntity())

    suspend fun update(order: ServiceOrderModel) =
        dao.update(order.toEntity())

    suspend fun insert(order: ServiceOrderEntity) : Long =
        dao.insert(order)

    suspend fun update(order: ServiceOrderEntity) =
        dao.update(order)

    suspend fun delete(order: ServiceOrderModel) =
        dao.delete(order.toEntity())

    fun listOrdersByMonth(month: String): Flow<List<ServiceOrderModel>> =
        dao.getOrdersByMonth(month)
            .map { list -> list.map { it.toDomain() } }

    fun listOrdersByWeek(week: String): Flow<List<ServiceOrderModel>> =
        dao.getOrdersByWeek(week)
            .map { list -> list.map { it.toDomain() } }

    fun listOrdersByWeekAndStatus(
        week: String,
        status: ServiceOrderStatus
    ): Flow<List<ServiceOrderModel>> =
        dao.getOrdersByWeekAndStatus(week, status.id)
            .map { list -> list.map { it.toDomain() } }
}