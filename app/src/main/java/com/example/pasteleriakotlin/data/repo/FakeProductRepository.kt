package com.example.pasteleriakotlin.data.repo

import com.example.pasteleriakotlin.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Repositorio falso (en memoria) con datos de ejemplo.
 * Dejamos la propiedad imageUrl para no romper el modelo,
 * aunque la UI muestra una imagen local (R.drawable.cake).
 */
class FakeProductRepository {
    private val items = MutableStateFlow(
        listOf(
            Product("1", "Torta Tres Leches", "Bizcocho húmedo con merengue", 12990, "local://cake", true),
            Product("2", "Cheesecake Frutilla", "Clásico con salsa de frutilla", 9990, "local://cake", true),
            Product("3", "Brownie Nuez", "Intenso con nueces tostadas", 3990, "local://cake", true)
        )
    )

    fun getProducts() = items
    fun getProductById(id: String) = items.map { list -> list.find { it.id == id } }
}

