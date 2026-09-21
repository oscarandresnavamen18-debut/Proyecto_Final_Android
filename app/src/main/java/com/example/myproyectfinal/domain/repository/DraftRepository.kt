package com.example.myproyectfinal.domain.repository

import com.example.myproyectfinal.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface DraftRepository {
    fun getDrafts(): Flow<List<TaskDraft>>
    suspend fun saveDraft(draft: TaskDraft)
    suspend fun updateDraft(draft: TaskDraft)
    suspend fun deleteDraft(id: Int)
    suspend fun getDraftById(id: Int): TaskDraft?
    suspend fun deleteAllDrafts()
}
