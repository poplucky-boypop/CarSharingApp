package com.example.carsharingapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(entity = UserLoginEntity::class)
    fun addUser(user: UserLoginEntity)

    @Query("SELECT id, password FROM user_login_table WHERE email = :email")
    suspend fun findByEmail(email: String): UserSingInTuple? //LiveData<List<User>>

    @Query("SELECT * FROM user_login_table WHERE id = :userId")
    suspend fun getById(userId: Long): UserLoginEntity //Flow<UserLoginEntity>

    @Query("SELECT * FROM car_info")
    suspend fun getAllCars(): List<CarInfo>

    @Query("SELECT * FROM car_info WHERE name LIKE '%'||:name||'%'")
    suspend fun getCarsByName(name: String): List<CarInfo>

    @Insert(entity = CarBookmarksEntity::class)
    suspend fun addBookmark(bookmark: CarBookmarksEntity)

    @Query("SELECT id_car_foreign FROM car_bookmarks WHERE id_user_foreign = :idUser")
    suspend fun findBookmarksByIdUser(idUser: Long): List<Long>

    @Query("SELECT * FROM car_info WHERE id IN (:listBookmarks)")
    fun findCarsInBookmarks(listBookmarks: List<Long>): Flow<List<CarInfo>> //List<CarInfo>

    @Query("SELECT * FROM car_bookmarks WHERE id_user_foreign = :idUser AND id_car_foreign = :idCar")
    suspend fun findCarInBookmarksById(idUser: Long, idCar: Long): CarBookmarksEntity
}