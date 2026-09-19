package com.example.myproyectfinal.data.local.dao

import androidx.room.*
import com.example.myproyectfinal.data.local.entity.TaskDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDraftDao {
    @Query("SELECT * FROM task_drafts ORDER BY createdAt DESC")
    fun getAllDrafts(): Flow<List<TaskDraftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: TaskDraftEntity)

    @Update
    suspend fun updateDraft(draft: TaskDraftEntity)

    @Query("DELETE FROM task_drafts WHERE id = :id")
    suspend fun deleteDraft(id: Int)

    @Query("SELECT * FROM task_drafts WHERE id = :id")
    suspend fun getDraftById(id: Int): TaskDraftEntity?
}
