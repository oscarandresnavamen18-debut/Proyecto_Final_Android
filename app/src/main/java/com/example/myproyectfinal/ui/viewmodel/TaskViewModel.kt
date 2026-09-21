package com.example.myproyectfinal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproyectfinal.domain.model.Task
import com.example.myproyectfinal.domain.usecase.*
import com.example.myproyectfinal.ui.state.OperationState
import com.example.myproyectfinal.ui.state.TaskListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _operationState = MutableStateFlow<OperationState>(OperationState.Idle)
    val operationState = _operationState.asStateFlow()

    val uiState: StateFlow<TaskListUiState> = getCurrentUserUseCase()
        .flatMapLatest { user ->
            if (user == null) flowOf(TaskListUiState())
            else getTasksUseCase(user.id).map { TaskListUiState(tasks = it) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskListUiState(isLoading = true))

    fun createTask(title: String, description: String) {
        viewModelScope.launch {
            _operationState.value = OperationState.Loading
            val user = getCurrentUserUseCase().first()
            if (user != null) {
                val result = createTaskUseCase(Task(title = title, description = description, userId = user.id))
                _operationState.value = if (result.isSuccess) OperationState.Success("Task created") else OperationState.Error("Error")
            }
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            _operationState.value = OperationState.Loading
            val result = deleteTaskUseCase(taskId)
            _operationState.value = if (result.isSuccess) OperationState.Success("Task deleted") else OperationState.Error("Error deleting task")
        }
    }

    fun resetOperationState() {
        _operationState.value = OperationState.Idle
    }
}
