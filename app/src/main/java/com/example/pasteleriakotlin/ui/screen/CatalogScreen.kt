package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.data.model.Product
import com.example.pasteleriakotlin.ui.catalog.CatalogUiState
import com.example.pasteleriakotlin.ui.components.CartSheetContent
import com.example.pasteleriakotlin.ui.components.ProductCard
import com.example.pasteleriakotlin.R

@Composable
fun CatalogContent(
    uiState: CatalogUiState,
    modifier: Modifier = Modifier,
    showCart: Boolean,
    onShowCartChange: (Boolean) -> Unit,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onPay: () -> Unit
) {
    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            modifier = modifier,
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

    if (showCart) {
        ModalBottomSheet(onDismissRequest = { onShowCartChange(false) }) {
            CartSheetContent(
                items = uiState.cartItems,
                total = uiState.total,
                onClose = { onShowCartChange(false) },
                onRemove = onRemoveFromCart,
                onPay = {
                    onPay()
                    onShowCartChange(false)
                }
            )
        }
    }
}

// Muestra el catalogo y permite ver/editar el carrito
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onBack: (() -> Unit)? = null,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onPay: () -> Unit
) {
    var showCart by remember { mutableStateOf(false) }
    val itemCount = uiState.cartItems.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.nav_catalog)) },
                navigationIcon = onBack?.let { handler ->
                    {
                        TextButton(onClick = handler) { Text("Atras") }
                    }
                },
                actions = {
                    BadgedBox(badge = { if (itemCount > 0) Badge { Text(itemCount.toString()) } }) {
                        IconButton(onClick = { showCart = true }) {
                            Icon(
                                Icons.Filled.ShoppingCart,
                                contentDescription = stringResource(R.string.nav_catalog)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        CatalogContent(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
            showCart = showCart,
            onShowCartChange = { showCart = it },
            onProductClick = onProductClick,
            onAddToCart = onAddToCart,
            onRemoveFromCart = onRemoveFromCart,
            onPay = onPay
        )
    }
}
