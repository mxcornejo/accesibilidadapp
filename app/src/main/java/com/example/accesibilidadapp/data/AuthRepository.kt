package com.example.accesibilidadapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val isLoggedIn: Boolean
        get() = auth.currentUser != null

    /**
     * Emite el usuario actual cada vez que cambia el estado de autenticación.
     * El primer valor se emite después de que Firebase restaura la sesión.
     */
    val currentUserFlow: Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { trySend(it.currentUser) }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    /**
     * Inicia sesión con email y contraseña via Firebase Auth.
     * @throws Exception con mensaje amigable si las credenciales son incorrectas.
     */
    suspend fun login(email: String, password: String): FirebaseUser {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user ?: throw Exception("Error al iniciar sesión")
        } catch (e: FirebaseAuthInvalidUserException) {
            throw Exception("No existe una cuenta con ese email")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw Exception("Email o contraseña incorrectos")
        }
    }

    /**
     * Crea una nueva cuenta con email y contraseña via Firebase Auth.
     * @throws Exception con mensaje amigable si el email ya está en uso.
     */
    suspend fun register(email: String, password: String): FirebaseUser {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user ?: throw Exception("Error al crear la cuenta")
        } catch (e: com.google.firebase.auth.FirebaseAuthUserCollisionException) {
            throw Exception("Este correo ya está registrado")
        } catch (e: com.google.firebase.auth.FirebaseAuthWeakPasswordException) {
            throw Exception("La contraseña debe tener al menos 6 caracteres")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw Exception("El formato del email no es válido")
        }
    }

    /**
     * Envía un correo de recuperación de contraseña al email indicado.
     */
    suspend fun sendPasswordReset(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logout() {
        auth.signOut()
    }
}
