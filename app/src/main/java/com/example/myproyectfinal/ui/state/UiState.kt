package com.example.myproyectfinal.ui.state

import com.example.myproyectfinal.domain.model.Task
import com.example.myproyectfinal.domain.model.TaskDraft

data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class DraftListUiState(
    val drafts: List<TaskDraft> = emptyList(),
    val isLoading: Boolean = false
)

sealed class OperationState {
    object Idle : OperationState()
    object Loading : OperationState()
    data class Success(val message: String? = null) : OperationState()
    data class Error(val message: String) : OperationState()
}
