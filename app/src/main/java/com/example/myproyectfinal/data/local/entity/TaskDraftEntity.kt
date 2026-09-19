package com.example.myproyectfinal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myproyectfinal.domain.model.TaskDraft

@Entity(tableName = "task_drafts")
data class TaskDraftEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val createdAt: Long
)

fun TaskDraftEntity.toDomain() = TaskDraft(id, title, description, createdAt)
fun TaskDraft.toEntity() = TaskDraftEntity(id, title, description, createdAt)
