package br.com.naitzel.hertz.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.naitzel.hertz.data.local.entity.ServiceOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceOrderDao {
    @Query("SELECT * FROM service_orders ORDER BY created_at DESC")
    suspend fun getAll(): List<ServiceOrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: ServiceOrderEntity): Long

    @Update
    suspend fun update(order: ServiceOrderEntity)

    @Delete
    suspend fun delete(order: ServiceOrderEntity)

    @Query("SELECT * FROM service_orders WHERE id = :id")
    suspend fun getById(id: Long): ServiceOrderEntity?

    @Query("SELECT * FROM service_orders WHERE id = :id")
    fun getByIdLiveData(id: Long): Flow<ServiceOrderEntity?>

    @Query(
        """
        SELECT * FROM service_orders
        WHERE strftime('%Y-%m', date_time/1000, 'unixepoch') = :month
        ORDER BY date_time DESC
    """
    )
    fun getOrdersByMonth(month: String): Flow<List<ServiceOrderEntity>>

    @Query(
        """
        SELECT * FROM service_orders
        WHERE strftime('%W', date_time/1000, 'unixepoch') = :week
        ORDER BY date_time DESC
    """
    )
    fun getOrdersByWeek(week: String): Flow<List<ServiceOrderEntity>>

    @Query(
        """
        SELECT * FROM service_orders
        WHERE strftime('%W', date_time/1000, 'unixepoch') = :week
          AND status = :status
        ORDER BY date_time DESC
    """
    )
    fun getOrdersByWeekAndStatus(week: String, status: Byte): Flow<List<ServiceOrderEntity>>

    @Query(
        """
        SELECT * FROM service_orders
        WHERE date_time BETWEEN :startMs AND :endMs
          AND status = :status
    """
    )
    fun getOrdersByDateRange(
        startMs: Long,
        endMs: Long,
        status: Byte
    ): Flow<List<ServiceOrderEntity>>
}