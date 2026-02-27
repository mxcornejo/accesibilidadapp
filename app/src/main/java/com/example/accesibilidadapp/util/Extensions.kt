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
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isStrongPassword(): Boolean {
    return this.length >= 6
}
