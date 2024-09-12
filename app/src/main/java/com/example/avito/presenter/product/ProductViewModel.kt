package com.example.avito.presenter.product

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _isProductLoaded = MutableStateFlow(false)
    val isProductLoaded: StateFlow<Boolean> = _isProductLoaded.asStateFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean>
        get() = _isLoading

    fun getProduct(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            _product.value = repository.getProduct(id)
            _isProductLoaded.value = true
        }
    }
    fun changeLoading(){
        _isLoading.value = false
    }
}