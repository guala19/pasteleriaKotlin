package com.example.pasteleriakotlin.ui.product

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
import kotlinx.coroutines.launch

data class ProductDetailUiState(
    val isLoading: Boolean = true,
    val product: Product? = null,
    val cartItems: List<CartItemUi> = emptyList(),
    val total: Int = 0
)

// Maneja un producto individual y el botón de carrito
class ProductDetailViewModel(
    private val productId: String,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        loadProduct()
        observeCart()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            productRepository.getProductById(productId).collectLatest { product ->
                _uiState.value = _uiState.value.copy(isLoading = false, product = product)
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
                _uiState.value = _uiState.value.copy(cartItems = mapped, total = total)
            }
        }
    }

    fun addToCart() {
        _uiState.value.product?.let { cartRepository.add(it) }
    }

    fun removeFromCart(productId: String) {
        cartRepository.remove(productId)
    }

    fun clearCart() {
        cartRepository.clear()
    }

    companion object {
        fun provideFactory(
            productId: String,
            productRepository: ProductRepository,
            cartRepository: CartRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductDetailViewModel(productId, productRepository, cartRepository) as T
                }
            }
        }
    }
}

