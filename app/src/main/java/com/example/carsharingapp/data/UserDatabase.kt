package com.example.carsharingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        UserInfo::class,
        UserLogin::class,
        CarInfo::class
    ]
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao

    //Далее он работает с однопоточностью
}