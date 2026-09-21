package com.example.myproyectfinal.data.repository

import com.example.myproyectfinal.data.local.dao.TaskDraftDao
import com.example.myproyectfinal.data.local.entity.toDomain
import com.example.myproyectfinal.data.local.entity.toEntity
import com.example.myproyectfinal.domain.model.TaskDraft
import com.example.myproyectfinal.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DraftRepositoryImpl @Inject constructor(
    private val dao: TaskDraftDao
) : DraftRepository {
    override fun getDrafts(): Flow<List<TaskDraft>> = 
        dao.getAllDrafts().map { list -> list.map { it.toDomain() } }

    override suspend fun saveDraft(draft: TaskDraft) = dao.insertDraft(draft.toEntity())

    override suspend fun updateDraft(draft: TaskDraft) = dao.updateDraft(draft.toEntity())

    override suspend fun deleteDraft(id: Int) = dao.deleteDraft(id)

    override suspend fun getDraftById(id: Int): TaskDraft? = dao.getDraftById(id)?.toDomain()

    override suspend fun deleteAllDrafts() = dao.deleteAllDrafts()
}
