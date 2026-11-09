package com.example.uts_catatanharian.data.repository

import com.example.uts_catatanharian.data.dao.UserDao_127
import com.example.uts_catatanharian.data.entity.UserEntity_127

class UserRepository_127(private val userDao: UserDao_127) {
    suspend fun registerUser_127(user: UserEntity_127): Long {
        return userDao.registerUser_127(user)
    }

    suspend fun getUserByUsername_127(username: String): UserEntity_127? {
        return userDao.getUserByUsername_127(username)
    }

    suspend fun getUserById_127(userId: Long): UserEntity_127? {
        return userDao.getUserById_127(userId)
    }
}
