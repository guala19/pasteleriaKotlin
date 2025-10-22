package com.example.pasteleriakotlin.data.repo

import com.example.pasteleriakotlin.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

// Guarda el carrito en memoria mientras usamos la app
class CartRepository {

    data class CartItem(val product: Product, val quantity: Int)

    private val itemsMap = MutableStateFlow<Map<String, CartItem>>(emptyMap())

    fun itemsFlow(): Flow<List<CartItem>> = itemsMap.map { it.values.toList() }

    fun totalFlow(): Flow<Int> =
        itemsMap.map { it.values.sumOf { item -> item.product.price * item.quantity } }

    fun add(product: Product) {
        itemsMap.update { current ->
            val existing = current[product.id]
            if (existing == null) {
                current + (product.id to CartItem(product, 1))
            } else {
                current + (product.id to existing.copy(quantity = existing.quantity + 1))
            }
        }
    }

    fun remove(productId: String) {
        itemsMap.update { current -> current - productId }
    }

    fun clear() {
        itemsMap.value = emptyMap()
    }
}
