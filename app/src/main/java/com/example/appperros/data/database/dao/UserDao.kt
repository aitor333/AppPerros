package com.example.appperros.data.database.dao

import androidx.room.*
import com.example.appperros.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY user_name DESC")
    fun getAllUsers() : Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(googleUser : UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

}