package com.example.myproyectfinal.data.repository

import com.example.myproyectfinal.domain.model.User
import com.example.myproyectfinal.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun getCurrentUser(): Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser?.let { User(it.uid, it.email ?: "", it.displayName ?: "") }
            trySend(user)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override suspend fun login(email: String, pass: String): Result<User> = try {
        val result = firebaseAuth.signInWithEmailAndPassword(email, pass).await()
        val user = result.user?.let { User(it.uid, it.email ?: "", it.displayName ?: "") }
        if (user != null) Result.success(user) else Result.failure(Exception("Login failed"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun register(name: String, email: String, pass: String): Result<User> = try {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
        // Here you would normally update the profile with name
        val user = result.user?.let { User(it.uid, it.email ?: "", name) }
        if (user != null) Result.success(user) else Result.failure(Exception("Registration failed"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}
