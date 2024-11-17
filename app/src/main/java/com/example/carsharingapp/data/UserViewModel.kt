package com.example.carsharingapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class UserViewModel(application: Application): AndroidViewModel(application) {
//class UserViewMode(
    //private val repository: UserRepository): ViewModel() {

    /*fun singIn(email: String, password: String) = viewModelScope.launch{
        try {
            val userId = repository.findUserIdByEmailAndPassword(email, password)
            emit(userId)
        } catch (e: Exception) {
            emit(null) // обработка ошибок
        }

    }



    //private val readUserLogin: List<UserLoginEntity>
    private val id: Long
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).getUserDao()
        repository = UserRepository(userDao)
        id = repository.findUserIdByEmailAndPassword()
    }

    fun addUser(user: UserLoginEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }*/

//}