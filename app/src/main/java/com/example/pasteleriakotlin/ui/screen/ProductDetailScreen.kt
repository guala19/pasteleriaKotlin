package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.R
import com.example.pasteleriakotlin.data.model.Product
import com.example.pasteleriakotlin.data.repo.FakeProductRepository
import kotlinx.coroutines.flow.collectLatest

/**
 * Pantalla de detalle de un producto.
 * Carga el producto por id y permite agregarlo al carrito.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(id: String, onBack: () -> Unit) {
    var p by remember { mutableStateOf<Product?>(null) }
    val repo = remember { FakeProductRepository() }

    // Cada vez que cambia el id, buscamos el producto
    LaunchedEffect(id) {
        repo.getProductById(id).collectLatest { product -> p = product }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(p?.name ?: "Detalle") },
            navigationIcon = { TextButton(onClick = onBack) { Text("Atrás") } }
        )
    }) { pad ->
        if (p == null) {
            Box(Modifier.padding(pad).padding(16.dp)) { Text("Cargando producto…") }
        } else {
            Column(Modifier.padding(pad).padding(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.cake),
                    contentDescription = p!!.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(p!!.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
                Text(p!!.description)
                Spacer(Modifier.height(12.dp))
                Text("Precio: CLP ${p!!.price}")
                Spacer(Modifier.height(16.dp))
                Button(onClick = { com.example.pasteleriakotlin.ui.state.CartManager.add(p!!) }) {
                    Text("Añadir al carrito")
                }
            }
        }
    }
}

