package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.data.model.Product
import com.example.pasteleriakotlin.data.repo.FakeProductRepository
import com.example.pasteleriakotlin.ui.components.ProductCard
import com.example.pasteleriakotlin.ui.state.CartManager
import kotlinx.coroutines.flow.collectLatest

/**
 * Pantalla del catálogo de productos.
 * Muestra una grilla con productos y un ícono de carrito con badge.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onBack: () -> Unit,
    onProductClick: (String) -> Unit
) {
    // Repositorio simple en memoria para listar productos
    val repo = remember { FakeProductRepository() }
    var products by remember { mutableStateOf(emptyList<Product>()) }
    LaunchedEffect(Unit) { repo.getProducts().collectLatest { products = it } }

    // Estado para mostrar/ocultar el carrito
    var showCart by remember { mutableStateOf(false) }
    // Flujo del carrito para calcular la cantidad total
    val cartItems by CartManager.itemsFlow().collectAsState(initial = emptyList())
    val itemCount = cartItems.sumOf { item -> item.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Atrás") } },
                actions = {
                    BadgedBox(badge = { if (itemCount > 0) Badge { Text(itemCount.toString()) } }) {
                        IconButton(onClick = { showCart = true }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        // Cuadrícula adaptativa para las tarjetas de producto
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products, key = { it.id }) { p ->
                ProductCard(
                    p,
                    onClick = { onProductClick(p.id) },
                    onAddToCart = { CartManager.add(p) }
                )
            }
        }
    }

    // Hoja modal con el contenido del carrito
    if (showCart) {
        ModalBottomSheet(onDismissRequest = { showCart = false }) {
            CartSheetContent(onClose = { showCart = false })
        }
    }
}

/**
 * Contenido de la hoja inferior (BottomSheet) para el carrito.
 */
@Composable
private fun CartSheetContent(onClose: () -> Unit) {
    val items by CartManager.itemsFlow().collectAsState(initial = emptyList())
    val total by CartManager.totalFlow().collectAsState(initial = 0)
    Column(Modifier.padding(16.dp)) {
        Text("Carrito", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        if (items.isEmpty()) {
            Text("Tu carrito está vacío")
        } else {
            // Lista simple de ítems con opción para eliminar
            items.forEach { ci ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${ci.product.name} x${ci.quantity}")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("CLP ${ci.product.price * ci.quantity}")
                        OutlinedButton(onClick = { CartManager.remove(ci.product.id) }) { Text("Eliminar") }
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text("Total: CLP $total", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onClose) { Text("Cerrar") }
                Button(onClick = { CartManager.clear(); onClose() }) { Text("Pagar") }
            }
        }
    }
}
