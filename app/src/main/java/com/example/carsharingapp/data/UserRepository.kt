package com.example.carsharingapp.data

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    /*suspend fun findUserIdByEmailAndPassword(email: String, password: String): Long {
        val tuple = userDao.findByEmail(email) ?: throw IllegalArgumentException()
        if (tuple.password != password) throw IllegalArgumentException()
        return tuple.id
    }

    //val readUserLogin: List<UserLogin> = userDao.getUserLogin()

    suspend fun addUser(user: UserLoginEntity) {
        //userDao.addUser(user)
        try {
            userDao.addUser(user)
        } catch (e: SQLiteConstraintException){
            val appException = IllegalArgumentException()
            appException.initCause(e)
            throw appException
        }
    }

    private fun getUserById(userId: Long): Flow<UserLoginEntity?> {
        return userDao.getById(userId)
    }*/
}