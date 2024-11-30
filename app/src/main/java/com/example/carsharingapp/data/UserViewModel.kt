package com.example.carsharingapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao): ViewModel() {
    fun getCarsInBookmarks(listBookmarks: List<Long>) =
        userDao.findCarsInBookmarks(listBookmarks).asLiveData()

    fun addBookmark(bookmark: CarBookmarksEntity) = viewModelScope.launch {
        viewModelScope.launch {
            userDao.addBookmark(bookmark) // Выполнение операции в фоновом потоке
        }
    }

    class Factory(private val userDao: UserDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(userDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
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