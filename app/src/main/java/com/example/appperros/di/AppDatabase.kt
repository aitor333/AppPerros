package com.example.appperros.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appperros.data.database.dao.UserDao
import com.example.appperros.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1,exportSchema = true)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun userDao(): UserDao

}
