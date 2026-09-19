package com.example.myproyectfinal.data.repository

import com.example.myproyectfinal.data.remote.model.TaskDocument
import com.example.myproyectfinal.data.remote.model.toDocument
import com.example.myproyectfinal.data.remote.model.toDomain
import com.example.myproyectfinal.domain.model.Task
import com.example.myproyectfinal.domain.repository.TaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TaskRepository {
    private val collection = firestore.collection("tasks")

    override fun getTasks(userId: String): Flow<List<Task>> = callbackFlow {
        val subscription = collection.whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val tasks = snapshot?.documents?.mapNotNull { it.toObject(TaskDocument::class.java)?.toDomain(it.id) } ?: emptyList()
                trySend(tasks)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun createTask(task: Task): Result<Unit> = try {
        collection.add(task.toDocument()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateTask(task: Task): Result<Unit> = try {
        collection.document(task.id).set(task.toDocument()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> = try {
        collection.document(taskId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
