package br.com.naitzel.hertz.presentation.ui.screens.service_order.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.naitzel.hertz.data.local.entity.ServiceOrderEntity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun OrderItemCard(
    budget: ServiceOrderEntity,
    dateFormat: SimpleDateFormat,
    currencyFormat: NumberFormat,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable(onClick = onEdit)
//                .padding(16.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(
//                        text = budget.customerName,
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = budget.device,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.outline
//                    )
//                }
//
//                // Status badges
//                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//                    if (budget.isCompleted) {
//                        Surface(
//                            shape = RoundedCornerShape(12.dp),
//                            color = Color(0xFF66BB6A).copy(alpha = 0.2f)
//                        ) {
//                            Icon(
//                                Icons.Default.CheckCircle,
//                                contentDescription = "Completed",
//                                modifier = Modifier
//                                    .padding(4.dp)
//                                    .size(16.dp),
//                                tint = Color(0xFF66BB6A)
//                            )
//                        }
//                    }
//                    if (budget.isPaid) {
//                        Surface(
//                            shape = RoundedCornerShape(12.dp),
//                            color = Color(0xFF42A5F5).copy(alpha = 0.2f)
//                        ) {
//                            Icon(
//                                Icons.Default.Paid,
//                                contentDescription = "Paid",
//                                modifier = Modifier
//                                    .padding(4.dp)
//                                    .size(16.dp),
//                                tint = Color(0xFF42A5F5)
//                            )
//                        }
//                    }
//                    if (budget.hasInvoice) {
//                        Surface(
//                            shape = RoundedCornerShape(12.dp),
//                            color = Color(0xFFFFA726).copy(alpha = 0.2f)
//                        ) {
//                            Icon(
//                                Icons.Default.Receipt,
//                                contentDescription = "Invoice",
//                                modifier = Modifier
//                                    .padding(4.dp)
//                                    .size(16.dp),
//                                tint = Color(0xFFFFA726)
//                            )
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = budget.description,
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.outline,
//                maxLines = 2
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text(
//                        text = "Time: ${budget.estimatedTime}h",
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.outline
//                    )
//                    Text(
//                        text = dateFormat.format(Date(budget.date)),
//                        style = MaterialTheme.typography.labelSmall,
//                        color = MaterialTheme.colorScheme.outline
//                    )
//                }
//
//                Text(
//                    text = currencyFormat.format(budget.getTotalCost()),
//                    style = MaterialTheme.typography.titleLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                IconButton(onClick = onEdit) {
//                    Icon(Icons.Default.Edit, "Edit")
//                }
//                IconButton(onClick = onDelete) {
//                    Icon(Icons.Default.Delete, "Delete", tint = Color.Red)
//                }
//            }
//        }
//    }
}