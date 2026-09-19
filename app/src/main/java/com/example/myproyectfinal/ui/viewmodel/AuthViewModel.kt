package com.example.myproyectfinal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproyectfinal.domain.usecase.GetCurrentUserUseCase
import com.example.myproyectfinal.domain.usecase.LoginUserUseCase
import com.example.myproyectfinal.domain.usecase.LogoutUserUseCase
import com.example.myproyectfinal.domain.usecase.RegisterUserUseCase
import com.example.myproyectfinal.ui.state.OperationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUseCase: RegisterUserUseCase,
    private val loginUseCase: LoginUserUseCase,
    private val logoutUseCase: LogoutUserUseCase,
    getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val currentUser = getCurrentUserUseCase().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _authState = MutableStateFlow<OperationState>(OperationState.Idle)
    val authState = _authState.asStateFlow()

    fun clearState() {
        _authState.value = OperationState.Idle
    }

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = OperationState.Error("Todos los campos son obligatorios")
            return
        }
        viewModelScope.launch {
            _authState.value = OperationState.Loading
            val result = loginUseCase(email, pass)
            _authState.value = if (result.isSuccess) OperationState.Success() else OperationState.Error(result.exceptionOrNull()?.message ?: "Login failed")
        }
    }

    fun register(name: String, email: String, pass: String, confirmPass: String) {
        if (name.isBlank() || email.isBlank() || pass.isBlank() || confirmPass.isBlank()) {
            _authState.value = OperationState.Error("Todos los campos son obligatorios")
            return
        }
        if (!email.contains("@") || !email.contains(".")) {
            _authState.value = OperationState.Error("Formato de correo inválido")
            return
        }
        if (pass.length < 6) {
            _authState.value = OperationState.Error("La contraseña debe tener al menos 6 caracteres")
            return
        }
        if (pass != confirmPass) {
            _authState.value = OperationState.Error("Las contraseñas no coinciden")
            return
        }
        viewModelScope.launch {
            _authState.value = OperationState.Loading
            val result = registerUseCase(name, email, pass)
            _authState.value = if (result.isSuccess) OperationState.Success() else OperationState.Error(result.exceptionOrNull()?.message ?: "Registration failed")
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}
