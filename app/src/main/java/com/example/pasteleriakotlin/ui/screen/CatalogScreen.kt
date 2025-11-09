package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.Top AppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.data.model.Product
import com.example.pasteleriakotlin.ui.catalog.CatalogUiState
import com.example.pasteleriakotlin.ui.components.CartSheetContent
import com.example.pasteleriakotlin.ui.components.ProductCard

// Muestra el catalogo con búsqueda, filtros y permite ver/editar el carrito
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onPay: () -> Unit,
    onSearchChange: (String) -> Unit,
    onCategorySelect: (String?) -> Unit
) {
    var showCart by remember { mutableStateOf(false) }
    val itemCount = uiState.cartItems.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuestro Catálogo") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Atrás") } },
                actions = {
                    BadgedBox(badge = { if (itemCount > 0) Badge { Text(itemCount.toString()) } }) {
                        IconButton(onClick = { showCart = true }) {
                            Icon(
                                Icons.Filled.ShoppingCart,
                                contentDescription = "Carrito"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Buscador
                TextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Buscar productos...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Limpiar")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )
                
                // Chips de categorías
                if (uiState.availableCategories.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = uiState.selectedCategory == null,
                                onClick = { onCategorySelect(null) },
                                label = { Text("Todos") }
                            )
                        }
                        items(uiState.availableCategories) { category ->
                            FilterChip(
                                selected = uiState.selectedCategory == category,
                                onClick = { onCategorySelect(category) },
                                label = { Text(category) }
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                // Grid de productos
                if (uiState.products.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("No encontramos productos")
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = { 
                                onSearchChange("")
                                onCategorySelect(null)
                            }) {
                                Text("Ver todos los productos")
                            }
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(180.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.products, key = { it.id }) { product ->
                            ProductCard(
                                p = product,
                                onClick = { onProductClick(product.id) },
                                onAddToCart = { onAddToCart(product) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showCart) {
        ModalBottomSheet(onDismissRequest = { showCart = false }) {
            CartSheetContent(
                items = uiState.cartItems,
                total = uiState.total,
                onClose = { showCart = false },
                onRemove = onRemoveFromCart,
                onPay = {
                    onPay()
                    showCart = false
                }
            )
        }
    }
}
