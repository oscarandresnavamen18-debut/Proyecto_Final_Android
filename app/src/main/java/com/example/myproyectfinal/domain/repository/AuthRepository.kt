package com.example.myproyectfinal.domain.repository

import com.example.myproyectfinal.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun login(email: String, pass: String): Result<User>
    suspend fun register(name: String, email: String, pass: String): Result<User>
    suspend fun logout()
}
