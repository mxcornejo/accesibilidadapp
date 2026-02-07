package com.example.accesibilidadapp.domain.usecase

import com.example.accesibilidadapp.model.User
import com.example.accesibilidadapp.util.isStrongPassword
import com.example.accesibilidadapp.util.isValidEmail

class InvalidUserException(message: String) : Exception(message)

class ValidateUserUseCase {

    fun validateEmail(email: String) {
        if (email.isBlank()) {
            throw InvalidUserException(message = "El email no puede estar vacío")
        }
        
        if (!email.isValidEmail()) {
            throw InvalidUserException(message = "El formato del email no es válido")
        }
    }

    fun validatePassword(password: String) {
        if (password.isBlank()) {
            throw InvalidUserException(message = "La contraseña no puede estar vacía")
        }
        
        if (!password.isStrongPassword()) {
            throw InvalidUserException(message = "La contraseña debe tener al menos 4 caracteres")
        }
    }

    fun validatePasswordMatch(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            throw InvalidUserException(message = "Las contraseñas no coinciden")
        }
    }

    fun filterUsersByAccessibility(
        users: List<User>,
        requireHighContrast: Boolean = false,
        disabilityTypes: List<String> = emptyList()
    ): List<User> {
        return users.filter { user ->
            val meetsContrastRequirement = !requireHighContrast || user.highContrastMode
            val meetsDisabilityFilter = disabilityTypes.isEmpty() || 
                                       user.disabilityType in disabilityTypes
            
            meetsContrastRequirement && meetsDisabilityFilter
        }
    }

    fun validateUserData(
        email: String,
        password: String,
        confirmPassword: String,
        acceptTerms: Boolean
    ): ValidationResult {
        
        val errors = mutableListOf<String>()
        
        run validation@ {
            if (email.isBlank()) {
                errors.add("El email es requerido")
                return@validation
            }
            
            if (!email.isValidEmail()) {
                errors.add("Email inválido")
            }
            
            if (password.isBlank()) {
                errors.add("La contraseña es requerida")
                return@validation
            }
            
            if (!password.isStrongPassword()) {
                errors.add("Contraseña muy débil")
            }
            
            if (password != confirmPassword) {
                errors.add("Las contraseñas no coinciden")
            }
            
            if (!acceptTerms) {
                errors.add("Debes aceptar los términos y condiciones")
                return@validation
            }
        }
        
        return if (errors.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(errors)
        }
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val errors: List<String>) : ValidationResult()
}
