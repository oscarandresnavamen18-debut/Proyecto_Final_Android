package com.example.myproyectfinal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproyectfinal.domain.model.TaskDraft
import com.example.myproyectfinal.domain.usecase.*
import com.example.myproyectfinal.ui.state.DraftListUiState
import com.example.myproyectfinal.ui.state.OperationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftViewModel @Inject constructor(
    private val getDraftsUseCase: GetDraftsUseCase,
    private val saveDraftUseCase: SaveDraftUseCase,
    private val deleteDraftUseCase: DeleteDraftUseCase,
    private val deleteAllDraftsUseCase: DeleteAllDraftsUseCase,
    private val publishDraftUseCase: PublishDraftUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val uiState: StateFlow<DraftListUiState> = getDraftsUseCase()
        .map { DraftListUiState(drafts = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DraftListUiState(isLoading = true))

    private val _operationState = MutableStateFlow<OperationState>(OperationState.Idle)
    val operationState = _operationState.asStateFlow()

    fun saveDraft(title: String, description: String) {
        viewModelScope.launch {
            saveDraftUseCase(TaskDraft(title = title, description = description))
        }
    }

    fun publishDraft(draftId: Int) {
        viewModelScope.launch {
            _operationState.value = OperationState.Loading
            val user = getCurrentUserUseCase().first()
            if (user != null) {
                val result = publishDraftUseCase(draftId, user.id)
                _operationState.value = if (result.isSuccess) OperationState.Success("Published") else OperationState.Error("Error")
            }
        }
    }

    fun deleteDraft(id: Int) {
        viewModelScope.launch {
            deleteDraftUseCase(id)
        }
    }

    fun deleteAllDrafts() {
        viewModelScope.launch {
            deleteAllDraftsUseCase()
        }
    }
}
