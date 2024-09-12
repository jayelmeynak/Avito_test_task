package com.example.avito.presenter.productList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avito.R
import com.example.avito.data.repository.ProductsRepository
import com.example.avito.domain.Category
import com.example.avito.domain.Product
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val repository = ProductsRepository()

    private val _products = mutableStateOf(emptyList<Product>())
    val products: MutableState<List<Product>> = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    private val _category = mutableStateOf("")
    private val _sort = mutableStateOf("")

    private val _activeCategory = mutableStateOf(-1)

    private val _activeSort = mutableStateOf(-1)
    val activeSort: State<Int>
        get() = _activeSort

    val expanded = mutableStateOf(false)


    val categoryList = listOf(
        Category(R.drawable.ic_footwear, "footwear", "Обувь"),
        Category(R.drawable.ic_clothing, "clothing", "Одежда"),
        Category(R.drawable.ic_furniture, "furniture", "Мебель"),
        Category(R.drawable.ic_computer, "computers", "Компьтерная техника"),
    )

    fun getAllProducts() {
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

    fun getProductsFilterByCategory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getProductsFilterByCategory(_category.value)
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

    fun getProductsWithPriceSort() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getProductsWithPriceSort(_sort.value)
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

    fun getProductsWithPriceSortAndCategory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response =
                    repository.getProductsWithPriceSortAndCategory(
                        category = _category.value,
                        sort = _sort.value
                    )
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

    fun setCategory(category: String, index: Int) {
        _activeCategory.value = if (_activeCategory.value == index) -1 else index
        _category.value = if (_activeCategory.value == -1) "" else category
        if (_category.value.isNotEmpty()) {
            if (_sort.value.isEmpty()) {
                getProductsFilterByCategory()
            } else {
                getProductsWithPriceSortAndCategory()
            }
        } else {
            getAllProducts()
        }
    }

    fun setSort(sort: Int) {
        _activeSort.value = if (_activeSort.value == sort) -1 else sort
        _sort.value = when (_activeSort.value) {
            0 -> "+price"
            1 -> "-price"
            else -> ""
        }
        if (_sort.value.isNotEmpty()) {
            if (_category.value.isEmpty()) {
                getProductsWithPriceSort()
            } else {
                getProductsWithPriceSortAndCategory()
            }
        } else {
            getAllProducts()
        }
    }
}