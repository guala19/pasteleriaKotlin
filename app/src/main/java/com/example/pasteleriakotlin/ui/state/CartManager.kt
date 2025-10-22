package com.example.pasteleriakotlin.ui.state

import com.example.pasteleriakotlin.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/**
 * Modelo de ítem en el carrito.
 * Guarda el producto y la cantidad.
 */
data class CartItem(val product: Product, val quantity: Int)

/**
 * Gestor simple del carrito en memoria usando StateFlow.
 * - itemsFlow(): lista observable de ítems
 * - totalFlow(): total observable en CLP
 * - add/removeOne/clear: operaciones básicas
 */
object CartManager {
    private val itemsMap = MutableStateFlow<Map<String, CartItem>>(emptyMap())

    fun itemsFlow(): Flow<List<CartItem>> = itemsMap.map { it.values.toList() }
    fun totalFlow(): Flow<Int> = itemsMap.map { it.values.sumOf { ci -> ci.product.price * ci.quantity } }

    fun add(product: Product) {
        itemsMap.update { current ->
            val existing = current[product.id]
            if (existing == null) current + (product.id to CartItem(product, 1))
            else current + (product.id to existing.copy(quantity = existing.quantity + 1))
        }
    }

    fun removeOne(productId: String) {
        itemsMap.update { current ->
            val existing = current[productId] ?: return@update current
            val newQuantity = existing.quantity - 1
            if (newQuantity <= 0) current - productId else current + (productId to existing.copy(quantity = newQuantity))
        }
    }

    /**
     * Elimina por completo un producto del carrito (independiente de su cantidad).
     */
    fun remove(productId: String) {
        itemsMap.update { current -> current - productId }
    }

    fun clear() { itemsMap.value = emptyMap() }
}
