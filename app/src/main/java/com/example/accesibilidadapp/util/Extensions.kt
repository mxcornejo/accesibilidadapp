package com.example.accesibilidadapp.util

import com.example.accesibilidadapp.model.User

val User.hasAccessibilityEnabled: Boolean
    get() = highContrastMode || disabilityType != "N/A"

fun User.formatFullName(): String {
    return name.trim().split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase() }
    }
}

fun User.getDescription(): String {
    return buildString {
        append("Usuario: ")
        append(formatFullName())
        append("\n")
        append("Email: ")
        append(email)
        append("\n")
        append("Tipo de discapacidad: ")
        append(disabilityType)
        append("\n")
        append("Modo alto contraste: ")
        append(if (highContrastMode) "Activado" else "Desactivado")
        append("\n")
        append("Accesibilidad: ")
        append(if (hasAccessibilityEnabled) "Configurada" else "Sin configurar")
    }
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return this.matches(emailRegex)
}

fun String.isStrongPassword(): Boolean {
    return this.length >= 4
}
