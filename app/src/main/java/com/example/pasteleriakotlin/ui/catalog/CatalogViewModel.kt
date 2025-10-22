package com.example.pasteleriakotlin.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pasteleriakotlin.data.model.Product
import com.example.pasteleriakotlin.data.repo.CartRepository
import com.example.pasteleriakotlin.data.repo.ProductRepository
import com.example.pasteleriakotlin.ui.cart.CartItemUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CatalogUiState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val cartItems: List<CartItemUi> = emptyList(),
    val total: Int = 0
)

// Mantiene la lista de productos y el estado del carrito
class CatalogViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        observeProducts()
        observeCart()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collectLatest { products ->
                _uiState.update { it.copy(products = products, isLoading = false) }
            }
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.itemsFlow().collectLatest { items ->
                val mapped = items.map {
                    CartItemUi(
                        id = it.product.id,
                        name = it.product.name,
                        quantity = it.quantity,
                        subtotal = it.product.price * it.quantity
                    )
                }
                val total = mapped.sumOf { it.subtotal }
                _uiState.update { it.copy(cartItems = mapped, total = total) }
            }
        }
    }

    fun addToCart(product: Product) {
        cartRepository.add(product)
    }

    fun removeFromCart(productId: String) {
        cartRepository.remove(productId)
    }

    fun clearCart() {
        cartRepository.clear()
    }

    companion object {
        fun provideFactory(
            productRepository: ProductRepository,
            cartRepository: CartRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CatalogViewModel(productRepository, cartRepository) as T
                }
            }
        }
    }
}
