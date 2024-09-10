package com.example.avito.presenter.auth

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.avito.presenter.navigation.Screens
import com.example.avito.ui.theme.Blue
import com.example.avito.ui.theme.Gray

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(context as ComponentActivity)

    val name by authViewModel.name

    val email by authViewModel.email
    val emailError by authViewModel.emailError

    val password by authViewModel.password
    val cPassword by authViewModel.cPassword
    val passwordError by authViewModel.cPasswordError
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val visualTransformation: VisualTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val isKeyboardOpen by keyboardAsState()

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
                    text = "Регистрация",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { authViewModel.onNameChange(it) },
                    label = { Text("Имя") },
                    singleLine = true,
                    placeholder = { Text("Введите имя") },
                    colors = OutlinedTextFieldDefaults.colors().copy(cursorColor = Gray)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        authViewModel.changeEmail(it)
                    },
                    label = { Text("Электронная почта") },
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
                    isError = passwordError,
                    colors = OutlinedTextFieldDefaults.colors().copy(cursorColor = Gray)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cPassword,
                    onValueChange = { authViewModel.onCPasswordChange(it) },
                    label = { Text("Пароль") },
                    visualTransformation = visualTransformation,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Введите пароль") },
                    singleLine = true,
                    isError = password != cPassword,
                    colors = OutlinedTextFieldDefaults.colors().copy(cursorColor = Gray),
                    trailingIcon = {
                        val image: ImageVector =
                            if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },

                )



                if (authViewModel.cPasswordError.value) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Пароли не совпадают", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(text = "Уже есть аккаунт?")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.clickable {
                            //navController.navigate(Screens.SignInScreen.route)
                        },
                        text = "Войти",
                        color = Blue
                    )

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
                onClick = { login(authViewModel, context, navController) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(text = "Войти")
            }
        }
    }
}

fun login(authViewModel: AuthViewModel, context: Context, navController: NavController) {
    if (authViewModel.emailError.value || authViewModel.passwordError.value || authViewModel.cPasswordError.value) {
        Toast.makeText(context, "Введите корректные данные", Toast.LENGTH_SHORT).show()
        return
    } else {
        authViewModel.login(
            onSuccess = {
                navController.navigate(Screens.SignInScreen.route)
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            },
            onError = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}