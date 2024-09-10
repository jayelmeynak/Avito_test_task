package com.example.avito.presenter.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avito.data.repository.ProductsRepository
import com.example.avito.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val repository = ProductsRepository()

    private val list = repository.getAllProducts()

    private val _products = MutableLiveData(list)
    val products: LiveData<List<Product>>
        get() = _products

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _isProductLoaded = MutableStateFlow(false)
    val isProductLoaded: StateFlow<Boolean> = _isProductLoaded.asStateFlow()

    fun getProduct(id: String) {
        viewModelScope.launch {
            _product.value = repository.getProduct(id)
            _isProductLoaded.value = true
        }
    }
    fun changeLoaded(){
        _isProductLoaded.value = false
    }
}