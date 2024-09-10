package com.example.avito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.avito.presenter.product.ProductScreen
import com.example.avito.ui.theme.AvitoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvitoTheme {
                ProductScreen(id = "64d7e740e03347cdf0b560d2")
            }
        }
    }
}
