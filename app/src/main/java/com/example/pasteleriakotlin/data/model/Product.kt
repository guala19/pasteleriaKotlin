package com.example.pasteleriakotlin.data.model

// Datos básicos que usamos en cada pantalla
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val available: Boolean = true
)
