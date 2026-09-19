package com.example.myproyectfinal.domain.usecase

import com.example.myproyectfinal.domain.model.User
import com.example.myproyectfinal.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, pass: String): Result<User> = 
        repository.register(name, email, pass)
}

class LoginUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, pass: String): Result<User> = 
        repository.login(email, pass)
}

class LogoutUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logout()
}

class GetCurrentUserUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(): Flow<User?> = repository.getCurrentUser()
}
