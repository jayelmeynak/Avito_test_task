package com.example.avito.presenter.productList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avito.data.repository.ProductsRepository
import com.example.avito.domain.Product
import kotlinx.coroutines.launch

class ProductListViewModel: ViewModel() {
    private val repository = ProductsRepository()

    private val _products = mutableStateOf(emptyList<Product>())
    val products: MutableState<List<Product>> = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    fun getProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getAllProducts()
                if (response.isSuccess) {
                    _products.value = response.getOrNull()!!
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }
}