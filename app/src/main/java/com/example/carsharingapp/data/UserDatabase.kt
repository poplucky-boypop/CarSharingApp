package com.example.carsharingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 3,
    entities = [
        //UserInfo::class,
        UserLoginEntity::class,
        CarInfo::class,
        CarBookmarksEntity::class,
        CarReservationEntity::class
    ]
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database.db"
                ).createFromAsset("inital_database.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

//.fallbackToDestructiveMigration()