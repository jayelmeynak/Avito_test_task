package com.example.avito.presenter.product

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.avito.domain.ProductSpecification
import com.example.avito.presenter.productList.Loading
import com.example.avito.ui.theme.LightSeparatorColor
import com.example.avito.ui.theme.Purple40
import com.example.avito.ui.theme.White
import kotlin.math.absoluteValue

@Composable
fun ProductScreen(id: String, navController: NavController) {

    val viewModel: ProductViewModel = viewModel()
    val product by viewModel.product.observeAsState()
    val isProductLoaded by viewModel.isProductLoaded.collectAsState()
    var name by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var images by remember {
        mutableStateOf(listOf<String>())
    }
    var specification by remember {
        mutableStateOf(listOf<ProductSpecification>())
    }
    var price by remember {
        mutableStateOf(0)
    }
    var discountPrice by remember {
        mutableStateOf(0)
    }


    LaunchedEffect(id) {
        Log.d("MyLog", id)
        viewModel.getProduct(id)
    }

    LaunchedEffect(isProductLoaded) {
        if (isProductLoaded) {
            product?.let { prod ->
                name = prod.name
                description = prod.description ?: ""
                images = prod.images
                specification = prod.productSpecifications
                price = prod.price
                discountPrice = prod.discountedPrice
            }
            viewModel.changeLoading()
        }
    }
    val pagerState = rememberPagerState(pageCount = { images.size })

    if (viewModel.isLoading.value){
        Loading()
    }
    else {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding())
                    .padding(horizontal = 16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(
                        bottom = innerPadding.calculateBottomPadding() + 80.dp,
                    )
                ) {
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }

                    item {
                        ImageCarousel(images, pagerState)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Price(price, discountPrice)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        NameText(name)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Specification(specification)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Description(description)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                BuyButton(innerPadding)
            }
        }
    }
}


@Composable
fun ImageCarousel(images: List<String>, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) { page ->
        Card(
            Modifier
                .width(250.dp)
                .height(300.dp)
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleX = lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleY = lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = images[page],
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "${page + 1} - ${pagerState.pageCount}",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(LightSeparatorColor)
                        .padding(horizontal = 8.dp, vertical = 1.dp),
                    color = White

                )
            }
        }
    }
}

@Composable
fun Price(price: Int, discountPrice: Int) {
    Column {
        Text(
            text = "${discountPrice} ₽",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${price} ₽",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                val width = size.width
                val height = size.height
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, height),
                    end = Offset(width, 0f),
                    strokeWidth = 4f
                )
            }
        }

    }
}

@Composable
fun NameText(name: String) {
    Text(
        text = name,
        fontSize = 24.sp
    )
}

@Composable
fun Description(description: String) {
    var isExpanded by remember { mutableStateOf(false) }
    val maxLength = 100

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isExpanded) {
            Text(
                text = description,
                fontSize = 16.sp
            )
        } else {
            Text(
                text = if (description.length > maxLength) {
                    description.substring(0, maxLength) + "..."
                } else {
                    description
                },
                fontSize = 16.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (description.length > maxLength && !isExpanded) {
            Text(
                text = "Читать далее",
                fontSize = 14.sp,
                modifier = Modifier.clickable { isExpanded = true },
                color = Color.Blue
            )
        }
    }
}

@Composable
fun Specification(specification: List<ProductSpecification>) {
    Column {
        Text(
            text = "Характеристики",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            specification.forEach { specification ->
                if (specification.key == null) return@forEach
                Row {
                    Text(
                        text = "${specification.key}:",
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = specification.value,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(2f)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun BoxScope.BuyButton(innerPadding: PaddingValues) {

    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(innerPadding)
            .padding(start = 32.dp, end = 32.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = Purple40
        ),
        onClick = { /*TODO*/ }
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = "Купить"
        )
    }
}


