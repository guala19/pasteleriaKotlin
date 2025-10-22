package com.example.pasteleriakotlin.data.repo

import com.example.pasteleriakotlin.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

// Lista de productos inventados para probar sin servidor
class FakeProductRepository : ProductRepository {
    private val items = MutableStateFlow(
        listOf(
            Product("1", "Torta Tres Leches", "Bizcocho humedo con merengue", 12990, "local://cake", true),
            Product("2", "Cheesecake Frutilla", "Clasico con salsa de frutilla", 9990, "local://cake", true),
            Product("3", "Brownie Nuez", "Intenso con nueces tostadas", 3990, "local://cake", true)
        )
    )

    override fun getProducts(): Flow<List<Product>> = items

    override fun getProductById(id: String): Flow<Product?> =
        items.map { list -> list.find { it.id == id } }
}
