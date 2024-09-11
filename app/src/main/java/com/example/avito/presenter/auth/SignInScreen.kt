package com.example.avito.presenter.auth

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.avito.ui.theme.Gray
import com.example.avito.ui.theme.Purple40

@Composable
fun SignInScreen(navController: NavController) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(context as ComponentActivity)

    val email by authViewModel.email
    val emailError by authViewModel.emailError

    val password by authViewModel.password
    val passwordError by authViewModel.passwordError
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val visualTransformation: VisualTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val isKeyboardOpen by keyboardAsState()

    var errorSignIn by authViewModel.error

    var errorMessage by authViewModel.errorMessage

    Scaffold(

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = innerPadding.calculateTopPadding() + 16.dp)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopStart),
            ) {
                Text(
                    text = "Вход",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        authViewModel.changeEmail(it)
                    },
                    label = { Text("Телефон или почта") },
                    isError = emailError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Введите почту") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    visualTransformation = VisualTransformation.None,
                    colors = OutlinedTextFieldDefaults.colors().copy(cursorColor = Gray)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { authViewModel.changePassword(it) },
                    label = { Text("Пароль") },
                    visualTransformation = visualTransformation,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Введите пароль") },
                    singleLine = true,
                    trailingIcon = {
                        val image: ImageVector =
                            if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors().copy(cursorColor = Gray),
                    isError = passwordError
                )

                if(errorSignIn){
                    Text(text = errorMessage)
                }
            }

            TextButton(
                modifier = Modifier
                    .padding(
                        bottom = if (!isKeyboardOpen) {
                            innerPadding.calculateBottomPadding() + 64.dp
                        } else innerPadding.calculateBottomPadding()
                    )
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                onClick = {
                    authViewModel.signIn(
                        onSuccess = {
                            errorSignIn = false
                            Toast.makeText(context, "Выполняется вход", Toast.LENGTH_SHORT).show()
//                            navController.navigate(Screens.MainScreen.route) {
//                                popUpTo(Screens.SignInScreen.route) {
//                                    inclusive = true
//                                }
//                            }
                        },
                        onError = { message ->
                            errorSignIn = true
                            errorMessage = message
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(text = "Вход")
            }
        }
    }
}