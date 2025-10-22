package com.example.pasteleriakotlin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.R
import com.example.pasteleriakotlin.data.model.Product
import java.text.NumberFormat
import java.util.Locale

// Tarjeta que muestra la foto, el precio y el boton de cada producto
@Composable
fun ProductCard(
    p: Product,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.cake),
                contentDescription = p.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            Column(Modifier.padding(12.dp)) {
                val formatter = NumberFormat.getNumberInstance(Locale("es", "CL")).apply {
                    maximumFractionDigits = 0
                }
                val formattedPrice = formatter.format(p.price.toLong())
                val displayPrice = "$" + formattedPrice
                Text(p.name, style = MaterialTheme.typography.titleMedium)
                Text(displayPrice, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = onAddToCart, enabled = p.available) { Text("Agregar") }
            }
        }
    }
}
