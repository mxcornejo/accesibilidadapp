package com.example.accesibilidadapp.model

data class User(
    val name: String,
    val email: String,
    val password: String,

    val disabilityType: String,
    val highContrastMode: Boolean,

    val acceptTerms: Boolean,
    val receiveNotifications: Boolean
)
