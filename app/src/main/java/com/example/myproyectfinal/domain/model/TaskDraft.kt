package com.example.myproyectfinal.domain.model

data class TaskDraft(
    val id: Int = 0,
    val title: String,
    val description: String,
    val createdAt: Long = System.currentTimeMillis()
)
