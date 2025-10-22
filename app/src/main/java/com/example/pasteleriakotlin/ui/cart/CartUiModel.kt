package com.example.pasteleriakotlin.ui.cart

// Representa lo que mostramos del carrito en la interfaz
data class CartItemUi(
    val id: String,
    val name: String,
    val quantity: Int,
    val subtotal: Int
)
