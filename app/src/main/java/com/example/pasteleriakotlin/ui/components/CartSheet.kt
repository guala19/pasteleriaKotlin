package com.example.pasteleriakotlin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.ui.cart.CartItemUi
import java.text.NumberFormat
import java.util.Locale

// Hoja inferior con el resumen del carrito
@Composable
fun CartSheetContent(
    items: List<CartItemUi>,
    total: Int,
    onClose: () -> Unit,
    onRemove: (String) -> Unit,
    onPay: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text("Carrito", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        if (items.isEmpty()) {
            Text("Tu carrito esta vacio")
        } else {
            val formatter = NumberFormat.getNumberInstance(Locale("es", "CL")).apply {
                maximumFractionDigits = 0
            }
            items.forEach { item ->
                val subtotalText = formatter.format(item.subtotal.toLong())
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("${item.name} x${item.quantity}")
                        Text("Subtotal: $" + subtotalText, style = MaterialTheme.typography.bodyMedium)
                    }
                    OutlinedButton(onClick = { onRemove(item.id) }) { Text("Eliminar") }
                }
                Spacer(Modifier.height(8.dp))
            }
            Spacer(Modifier.height(12.dp))
            val totalText = formatter.format(total.toLong())
            Text("Total: $" + totalText, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onClose) { Text("Cerrar") }
                Button(onClick = onPay) { Text("Pagar") }
            }
        }
    }
}
