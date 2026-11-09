package com.example.uts_catatanharian.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.uts_catatanharian.data.entity.UserEntity_127

@Dao
interface UserDao_127 {
    @Insert
    suspend fun registerUser_127(user: UserEntity_127): Long

    @Query("SELECT * FROM users_127 WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername_127(username: String): UserEntity_127?

    @Query("SELECT * FROM users_127 WHERE id = :userId LIMIT 1")
    suspend fun getUserById_127(userId: Long): UserEntity_127?
}
