package com.example.avito.presenter.productList

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.avito.R
import com.example.avito.domain.Category
import com.example.avito.domain.Product
import com.example.avito.presenter.navigation.Screens
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Composable
fun ProductListScreen(navController: NavController) {
    val viewModel: ProductListViewModel = viewModel(LocalContext.current as ComponentActivity)
    val errorMessage by viewModel.errorMessage
    LaunchedEffect(Unit) {
        if (viewModel.products.value.isEmpty()) {
            viewModel.getAllProducts()
        }
    }
    if (viewModel.isLoading.value && viewModel.products.value.isEmpty()) {
        Loading()
    } else if (errorMessage == null) {
        ScreenContent(
            viewModel.products.value,
            navController,
            viewModel.expanded,
            viewModel.activeSort,
            viewModel.categoryList,
            onClickCategory = {selectedCategory, index ->
                viewModel.setCategory(selectedCategory, index)
            },
            onClickSort = { selectedSort->
                viewModel.setSort(selectedSort)
            }
        )
    }else{

        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorMessage!!,
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.getAllProducts()
            }) {
                Text("Попробовать снова", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun ScreenContent(
    productList: List<Product>,
    navController: NavController,
    expanded: MutableState<Boolean>,
    activeSort: State<Int>,
    categoryList: List<Category>,
    onClickCategory: (String, Int) -> Unit,
    onClickSort: (Int) -> Unit
) {
    Scaffold { innerPadding ->
        Column {

            CategoryRow(innerPadding, categoryList, onClickCategory)

            Spacer(modifier = Modifier.height(8.dp))

            SortView(expanded, activeSort, onClickSort)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(productList.size, key = { productList[it].id }) { index ->
                    CardProduct(product = productList[index], navController = navController)
                }
            }
        }
    }
}

@Composable
fun CategoryRow(
    innerPadding: PaddingValues,
    categoryList: List<Category>,
    onClickCategory: (String, Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = innerPadding.calculateTopPadding() + 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        categoryList.forEachIndexed { index, category ->
            Image(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .clickable {
                        onClickCategory(category.categoryName, index)
                    },
                painter = painterResource(id = category.image),
                contentDescription = category.name,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun SortView(
    expanded: MutableState<Boolean>,
    activeSort: State<Int>,
    onClickSort: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Сортировка",
            modifier = Modifier.align(Alignment.Start)
        )
        Box(
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable {
                        expanded.value = true
                    },
                text =
                when (activeSort.value) {
                    0 -> "По возрастанию цены"
                    1 -> "По убыванию цены"
                    else -> "Отсутствует"
                },
                color = MaterialTheme.colorScheme.onTertiary
            )
            DropMenu(
                expanded = expanded.value,
                onSelectedClickListener = onClickSort,
                onDismissClickListener = {
                    expanded.value = it
                }
            )
        }
    }
}


@Composable
fun DropMenu(
    expanded: Boolean,
    onSelectedClickListener: (Int) -> Unit,
    onDismissClickListener: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissClickListener(false) },
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary)
    ) {
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(-1)
                onDismissClickListener(false)
            },
            text = { Text("Отсутствует") }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(0)
                onDismissClickListener(false)
            },
            text = { Text("По возрастанию цены") }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(1)
                onDismissClickListener(false)
            },
            text = { Text("По убыванию цены") }
        )
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

            Text(text = product.name, fontSize = 11.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${product.discountedPrice} ₽",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(1.dp))

            if (product.price != product.discountedPrice) {

                Text(
                    text = "${product.price} ₽",
                    fontSize = 11.sp,
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
            .placeholder(R.drawable.ic_image_search)
            .error(R.drawable.ic_placeholder)
            .build(),
        contentDescription = "Product image",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(150.dp, 200.dp)
            .clip(RoundedCornerShape(8.dp))
            .align(Alignment.CenterHorizontally),
        imageLoader = imageLoader
    )
}

