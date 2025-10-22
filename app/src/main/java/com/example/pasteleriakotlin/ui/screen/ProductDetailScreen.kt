package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.R
import com.example.pasteleriakotlin.ui.components.CartSheetContent
import com.example.pasteleriakotlin.ui.product.ProductDetailUiState
import java.text.NumberFormat
import java.util.Locale

// Muestra el detalle de un producto y deja sumarlo al carrito
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    onBack: () -> Unit,
    onAddToCart: () -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onPay: () -> Unit
) {
    val product = uiState.product
    var showCart by remember { mutableStateOf(false) }
    val itemCount = uiState.cartItems.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: "Detalle") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Atras") } },
                actions = {
                    BadgedBox(badge = { if (itemCount > 0) Badge { Text(itemCount.toString()) } }) {
                        IconButton(onClick = { showCart = true }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                product == null -> {
                    Text("Producto no encontrado", modifier = Modifier.align(Alignment.Center))
                }

                else -> {
                    val priceText = NumberFormat.getNumberInstance(Locale("es", "CL")).apply {
                        maximumFractionDigits = 0
                    }.format(product.price.toLong())
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cake),
                            contentDescription = product.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(product.name, style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(8.dp))
                        Text(product.description)
                        Spacer(Modifier.height(12.dp))
                        Text("Precio: $" + priceText)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = onAddToCart) { Text("Anadir al carrito") }
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
}


