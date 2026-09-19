package com.example.myproyectfinal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myproyectfinal.data.local.dao.TaskDraftDao
import com.example.myproyectfinal.data.local.entity.TaskDraftEntity

@Database(entities = [TaskDraftEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDraftDao(): TaskDraftDao
}
