package br.com.naitzel.hertz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.naitzel.hertz.data.local.entity.FinancialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FinancialDao {
    @Query("SELECT * FROM financial ORDER BY created_at DESC")
    suspend fun getAll(): List<FinancialEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: FinancialEntity)

    @Update
    suspend fun update(order: FinancialEntity)

    @Delete
    suspend fun delete(order: FinancialEntity)

    @Query("SELECT * FROM financial WHERE id = :id")
    suspend fun getById(id: Long): FinancialEntity?

    @Query("SELECT * FROM financial WHERE id = :id")
    fun getByIdLiveData(id: Long): LiveData<FinancialEntity?>

    @Query("""
        SELECT * FROM financial fr
        JOIN service_orders so ON fr.order_id = so.id
        WHERE strftime('%W', fr.date_time/1000, 'unixepoch') = :week
          AND fr.status = :status
        ORDER BY fr.date_time DESC
    """)
    fun getRecordsByWeekAndStatus(week: String, status: Byte): Flow<List<FinancialEntity>>
}