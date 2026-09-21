package com.example.myproyectfinal.domain.usecase

import com.example.myproyectfinal.domain.model.Task
import com.example.myproyectfinal.domain.model.TaskDraft
import com.example.myproyectfinal.domain.repository.DraftRepository
import com.example.myproyectfinal.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveDraftUseCase @Inject constructor(private val repository: DraftRepository) {
    suspend operator fun invoke(draft: TaskDraft) = repository.saveDraft(draft)
}

class GetDraftsUseCase @Inject constructor(private val repository: DraftRepository) {
    operator fun invoke(): Flow<List<TaskDraft>> = repository.getDrafts()
}

class UpdateDraftUseCase @Inject constructor(private val repository: DraftRepository) {
    suspend operator fun invoke(draft: TaskDraft) = repository.updateDraft(draft)
}

class DeleteDraftUseCase @Inject constructor(private val repository: DraftRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteDraft(id)
}

class DeleteAllDraftsUseCase @Inject constructor(private val repository: DraftRepository) {
    suspend operator fun invoke() = repository.deleteAllDrafts()
}

class PublishDraftUseCase @Inject constructor(
    private val draftRepository: DraftRepository,
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(draftId: Int, userId: String): Result<Unit> {
        val draft = draftRepository.getDraftById(draftId) ?: return Result.failure(Exception("Draft not found"))
        val task = Task(title = draft.title, description = draft.description, userId = userId)
        val result = taskRepository.createTask(task)
        if (result.isSuccess) {
            draftRepository.deleteDraft(draftId)
        }
        return result
    }
}
