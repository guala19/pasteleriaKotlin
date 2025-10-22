package com.example.pasteleriakotlin.data.model

/**
 * Modelo sencillo de producto.
 * - price se interpreta en CLP (pesos chilenos)
 * - imageUrl se mantiene por compatibilidad, pero la UI usa una imagen local
 */
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,        // CLP
    val imageUrl: String,
    val available: Boolean = true
)
