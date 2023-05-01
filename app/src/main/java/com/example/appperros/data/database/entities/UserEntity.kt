package com.example.appperros.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "id_user") val id: Int,
    @ColumnInfo(name = "user_name") val user_name: String,
    @ColumnInfo(name = "correo_user") val correo_user: String,
    @ColumnInfo(name = "rol_user") val rol_user: Int
)
