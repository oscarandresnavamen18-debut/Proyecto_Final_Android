package com.example.myproyectfinal.domain.usecase

import com.example.myproyectfinal.domain.model.Task
import com.example.myproyectfinal.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.createTask(task)
}

class GetTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(userId: String): Flow<List<Task>> = repository.getTasks(userId)
}

class UpdateTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.updateTask(task)
}

class DeleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: String) = repository.deleteTask(taskId)
}
