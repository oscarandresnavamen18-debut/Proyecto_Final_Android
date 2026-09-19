package com.example.myproyectfinal.data.remote.model

import com.example.myproyectfinal.domain.model.Task

data class TaskDocument(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val userId: String = ""
)

fun TaskDocument.toDomain(docId: String) = Task(
    id = docId,
    title = title,
    description = description,
    isCompleted = completed,
    userId = userId
)

fun Task.toDocument() = TaskDocument(
    title = title,
    description = description,
    completed = isCompleted,
    userId = userId
)
