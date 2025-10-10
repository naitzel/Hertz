package br.com.naitzel.hertz.data.local.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Keep
@Entity(
    tableName = "service_orders",
    indices = [
        Index("customer_name"),
        Index("status"),
        Index("created_at")
    ]
)
data class ServiceOrderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "customer_name")
    val customerName: String,

    @ColumnInfo(name = "customer_phone")
    val customerPhone: String,

    @ColumnInfo(name = "customer_email")
    val customerEmail: String,

    @ColumnInfo(name = "equipment")
    val equipment: String,

    @ColumnInfo(name = "equipment_number")
    val equipmentNumber: String,

    @ColumnInfo(name = "equipment_observation")
    val equipmentObservation: String,

    @ColumnInfo(name = "date_time")
    val dateTime: Long,

    @ColumnInfo(name = "service_time")
    val serviceTime: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "labor_cost")
    val laborCost: Double,

    @ColumnInfo(name = "parts_cost")
    val partsCost: Double,

    @ColumnInfo(name = "third_party_cost")
    val thirdPartyCost: Double,

    @ColumnInfo(name = "general_costs")
    val generalCosts: Double,

    @ColumnInfo(name = "should_issue_invoice")
    val shouldIssueInvoice: Boolean,

    @ColumnInfo(name = "additional_info")
    val additionalInfo: String,

    @ColumnInfo(name = "status")
    val status: Byte,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
)
