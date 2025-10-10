package br.com.naitzel.hertz.data.local.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Keep
@Entity(
    tableName = "financial",
    indices = [
        Index("order_id"),
    ],
    foreignKeys = [
        ForeignKey(
            entity = ServiceOrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["order_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class FinancialEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "order_id")
    val serviceOrderId: Long,

    @ColumnInfo(name = "date_time")
    val dateTime: Long,

    @ColumnInfo(name = "status")
    val status: Byte,

    @ColumnInfo(name = "total_value")
    val totalValue: Double,

    @ColumnInfo(name = "paid_value")
    val paidValue: Double,

    @ColumnInfo(name = "payment_date_time")
    val paymentDateTime: Long?,

    @ColumnInfo(name = "additional_info")
    val additionalInfo: String,

    @ColumnInfo(name = "installment_number")
    val installmentNumber: Int,

    @ColumnInfo(name = "total_installments")
    val totalInstallments: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Long
)