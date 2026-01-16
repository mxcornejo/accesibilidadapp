package com.example.accesibilidadapp.data

import androidx.compose.runtime.mutableStateListOf
import com.example.accesibilidadapp.model.User

object UserRepository {

    private val users = mutableStateListOf<User>()

    init {
        users.addAll(
            listOf(
                User(
                    name = "Juan Pérez",
                    email = "juan@gmail.com",
                    password = "1234",
                    disabilityType = "Visual",
                    highContrastMode = true,
                    acceptTerms = true,
                    receiveNotifications = true
                ),
                User(
                    name = "María López",
                    email = "maria@gmail.com",
                    password = "abcd",
                    disabilityType = "Auditiva",
                    highContrastMode = false,
                    acceptTerms = true,
                    receiveNotifications = false
                ),
                User(
                    name = "Carlos Soto",
                    email = "carlos@gmail.com",
                    password = "0000",
                    disabilityType = "Del habla",
                    highContrastMode = true,
                    acceptTerms = true,
                    receiveNotifications = true
                ),
                User(
                    name = "Fernanda Díaz",
                    email = "fer@gmail.com",
                    password = "pass1",
                    disabilityType = "Otra",
                    highContrastMode = false,
                    acceptTerms = true,
                    receiveNotifications = true
                ),
                User(
                    name = "Pedro Ramírez",
                    email = "pedro@gmail.com",
                    password = "1234",
                    disabilityType = "Visual",
                    highContrastMode = true,
                    acceptTerms = true,
                    receiveNotifications = false
                )
            )
        )
    }

    fun getUsers(): List<User> = users

    fun addUser(user: User): Boolean {
        val exists = users.any { it.email.equals(user.email, ignoreCase = true) }
        if (exists) return false

        users.add(user)
        return true
    }

    fun validateLogin(email: String, password: String): Boolean {
        return users.any {
            it.email.equals(email, ignoreCase = true) && it.password == password
        }
    }

    fun existsEmail(email: String): Boolean {
        return users.any { it.email.equals(email, ignoreCase = true) }
    }
}
