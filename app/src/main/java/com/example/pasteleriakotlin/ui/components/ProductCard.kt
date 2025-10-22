package com.example.pasteleriakotlin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.R
import com.example.pasteleriakotlin.data.model.Product

/**
 * Tarjeta de producto para usar en el catálogo.
 * Muestra la imagen local, nombre y precio. También permite agregar al carrito.
 */
@Composable
fun ProductCard(
    p: Product,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier.clickable { onClick() }.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.cake),
                contentDescription = p.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            Column(Modifier.padding(12.dp)) {
                Text(p.name, style = MaterialTheme.typography.titleMedium)
                Text("CLP ${p.price}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = onAddToCart, enabled = p.available) { Text("Agregar") }
            }
        }
    }
}
