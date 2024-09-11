package com.example.avito.presenter.productList

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.avito.R
import com.example.avito.domain.Product
import com.example.avito.presenter.navigation.Screens
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Composable
fun ProductListScreen(navController: NavController) {
    val viewModel: ProductListViewModel = viewModel(LocalContext.current as ComponentActivity)
    LaunchedEffect(Unit) {
        if (viewModel.products.value.isEmpty()) {
            viewModel.getProducts()
        }
    }
    if (viewModel.isLoading.value) {
        Loading()
    } else {
        ScreenContent(viewModel.products.value, navController)
    }
}

@Composable
fun ScreenContent(productList: List<Product>, navController: NavController) {
    Scaffold { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(productList.size, key = {productList[it].id}) { index ->
                CardProduct(product = productList[index], navController = navController)
            }
        }
    }
}

@Composable
fun CardProduct(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                navController.navigate(Screens.ProductScreen.route + "/${product.id}")
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)) {
            ProductImage(product = product)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = product.name)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${product.discountedPrice} ₽",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(1.dp))

            if (product.price != product.discountedPrice) {

                Text(
                    text = "${product.price} ₽",
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }
}

@Composable
fun ColumnScope.ProductImage(product: Product) {
    val customOkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .okHttpClient(customOkHttpClient)
        .build()

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(if (product.images.isEmpty()) R.drawable.ic_placeholder else product.images[0])
            .crossfade(true)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .build(),
        contentDescription = "Product image",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(150.dp, 200.dp)
            .align(Alignment.CenterHorizontally),
        imageLoader = imageLoader
    )
}

