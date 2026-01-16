package com.example.accesibilidadapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.accesibilidadapp.R
import com.example.accesibilidadapp.data.UserRepository
import com.example.accesibilidadapp.model.User
import com.example.accesibilidadapp.ui.components.AppOutlinedTextField
import com.example.accesibilidadapp.ui.theme.BackgroundLigth
import com.example.accesibilidadapp.ui.theme.BrandOrange
import com.example.accesibilidadapp.ui.theme.TextDark
import com.example.accesibilidadapp.ui.theme.TextMuted

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit = {},
    onRegisterSubmit: () -> Unit = {}
) {
    var rut by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val canSubmit = rut.isNotBlank() &&
            nombre.isNotBlank() &&
            apellidoPaterno.isNotBlank() &&
            apellidoMaterno.isNotBlank() &&
            region.isNotBlank() &&
            comuna.isNotBlank() &&
            telefono.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            password == confirmPassword &&
            agree

    Surface(modifier = Modifier.fillMaxSize(), color = BackgroundLigth) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(26.dp))
            Spacer(Modifier.height(60.dp))

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

            Spacer(Modifier.height(14.dp))

            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Regístrate para comenzar a usar la app.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )

            Spacer(Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {

                    Text(
                        text = "Información Personal",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(14.dp))

                    AppOutlinedTextField(
                        value = rut,
                        onValueChange = { rut = it },
                        label = "RUT",
                        placeholder = "Ej: 12.345.678-9",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(Modifier.height(12.dp))

                    AppOutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = "Nombre",
                        placeholder = "Ej: Juan",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(Modifier.height(12.dp))

                    AppOutlinedTextField(
                        value = apellidoPaterno,
                        onValueChange = { apellidoPaterno = it },
                        label = "Apellido Paterno",
                        placeholder = "Ej: Pérez",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(Modifier.height(12.dp))

                    AppOutlinedTextField(
                        value = apellidoMaterno,
                        onValueChange = { apellidoMaterno = it },
                        label = "Apellido Materno",
                        placeholder = "Ej: González",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(Modifier.height(12.dp))

                    AppOutlinedTextField(
                        value = region,
                        onValueChange = { region = it },
                        label = "Región",
                        placeholder = "Ej: Metropolitana",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(Modifier.height(12.dp))

                    AppOutlinedTextField(
                        value = comuna,
                        onValueChange = { comuna = it },
                        label = "Comuna",
                        placeholder = "Ej: Santiago",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(Modifier.height(12.dp))

                    AppOutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = "Teléfono",
                        placeholder = "+56 9 1234 5678",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Phone
                    )

                    Spacer(Modifier.height(16.dp))
                    Divider(color = BackgroundLigth, thickness = 1.dp)
                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Credenciales de Acceso",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(14.dp))

                    AppOutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Correo",
                        placeholder = "Ej: correo@gmail.com",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Email
                    )

                    Spacer(Modifier.height(12.dp))

                    AppOutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        placeholder = "********",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Lock,
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        trailingIconDescription = "Mostrar/Ocultar contraseña",
                        onTrailingIconClick = { showPassword = !showPassword }
                    )

                    Spacer(Modifier.height(12.dp))

                    AppOutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "Confirmar Contraseña",
                        placeholder = "********",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Lock,
                        visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        trailingIconDescription = "Mostrar/Ocultar contraseña",
                        onTrailingIconClick = { showConfirmPassword = !showConfirmPassword }
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = agree,
                            onCheckedChange = { agree = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFE91E63),
                                uncheckedColor = TextMuted,
                                checkmarkColor = Color.White
                            )
                        )
                        Text(
                            text = "Acepto términos y condiciones",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Button(
                        onClick = {
                            val newUser = User(
                                name = "$nombre $apellidoPaterno $apellidoMaterno",
                                email = email,
                                password = password,
                                disabilityType = "N/A",
                                highContrastMode = false,
                                acceptTerms = agree,
                                receiveNotifications = false
                            )

                            val added = UserRepository.addUser(newUser)

                            if (added) {
                                successMessage = "Usuario registrado correctamente"
                                errorMessage = null
                                onRegisterSubmit()
                            } else {
                                errorMessage = "Este correo ya está registrado"
                                successMessage = null
                            }
                        },
                        enabled = canSubmit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE91E63),
                            disabledContainerColor = Color(0xFFE91E63).copy(alpha = 0.35f)
                        )
                    ) {
                        Text(
                            text = "Registrarse",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (successMessage != null) {
                        Text(
                            text = successMessage!!,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLoginClick() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "¿Ya tienes cuenta?",
                            color = TextMuted
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "Inicia sesión",
                            color = BrandOrange,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(22.dp))
        }
    }
}
