package com.example.accesibilidadapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para guardar y leer datos de perfil de usuario en Firestore.
 * Cada documento se identifica por el UID de Firebase Auth.
 */
object FirestoreUserRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    /**
     * Guarda o reemplaza el perfil de un usuario en Firestore.
     */
    suspend fun saveUserProfile(uid: String, data: Map<String, Any>) {
        usersCollection.document(uid).set(data).await()
    }

    /**
     * Obtiene el perfil de un usuario desde Firestore.
     * Retorna null si el documento no existe.
     */
    suspend fun getUserProfile(uid: String): Map<String, Any>? {
        val doc = usersCollection.document(uid).get().await()
        return if (doc.exists()) doc.data else null
    }
}
