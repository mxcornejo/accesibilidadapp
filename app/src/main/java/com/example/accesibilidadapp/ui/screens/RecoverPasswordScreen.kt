package com.example.accesibilidadapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.accesibilidadapp.R
import com.example.accesibilidadapp.data.AuthRepository
import com.example.accesibilidadapp.ui.components.AppOutlinedTextField
import com.example.accesibilidadapp.ui.theme.BackgroundLigth
import com.example.accesibilidadapp.ui.theme.TextDark
import com.example.accesibilidadapp.ui.theme.TextMuted
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun RecoverPasswordScreenPreview() {
    RecoverPasswordScreen()
}

@Composable
fun RecoverPasswordScreen(
    onSendClick: (String) -> Unit = {},
    onBackToLoginClick: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val pink = Color(0xFFE91E63)
    val canSend = email.isNotBlank()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLigth
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(26.dp))
            Spacer(modifier = Modifier.height(60.dp))


            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(92.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Recuperar contraseña",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Te enviaremos un correo para restablecer tu contraseña.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(18.dp))


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {

                    Text(
                        text = "Ingresa tu Email",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    AppOutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeholder = "Ej: correo@gmail.com",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        leadingIcon = Icons.Filled.Email
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                errorMessage = null
                                successMessage = null
                                try {
                                    AuthRepository.sendPasswordReset(email)
                                    successMessage = "Correo enviado. Revisa tu bandeja de entrada."
                                    isLoading = false
                                } catch (e: Exception) {
                                    errorMessage = e.message ?: "Error al enviar el correo"
                                    isLoading = false
                                }
                            }
                        },
                        enabled = canSend && !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = pink,
                            disabledContainerColor = pink.copy(alpha = 0.35f)
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(22.dp)
                            )
                        } else {
                            Text(
                                text = "Enviar correo",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (successMessage != null) {
                        Text(
                            text = successMessage!!,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onBackToLoginClick() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "¿Recordaste tu contraseña?",
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Volver al login",
                            color = pink,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))
        }
    }
}
