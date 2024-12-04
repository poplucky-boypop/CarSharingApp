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

    @Query("SELECT * FROM car_info WHERE id = :carId")
    suspend fun getCarById(carId: Long): CarInfo

    @Insert(entity = CarBookmarksEntity::class)
    suspend fun addBookmark(bookmark: CarBookmarksEntity)

    @Query("SELECT id_car_foreign FROM car_bookmarks WHERE id_user_foreign = :idUser")
    suspend fun findBookmarksByIdUser(idUser: Long): List<Long>

    @Query("SELECT * FROM car_info WHERE id IN (:listBookmarks)")
    fun findCarsInBookmarks(listBookmarks: List<Long>): Flow<List<CarInfo>> //List<CarInfo>

    @Query("SELECT * FROM car_bookmarks WHERE id_user_foreign = :idUser AND id_car_foreign = :idCar")
    suspend fun findCarInBookmarksById(idUser: Long, idCar: Long): CarBookmarksEntity

    @Insert(entity = CarReservationEntity::class)
    fun addReservation(reservation: CarReservationEntity)

    @Query("DELETE FROM car_bookmarks WHERE id_user_foreign = :idUser AND id_car_foreign = :idCar")
    fun deleteBookmark(idUser: Long, idCar: Long)

    @Query("SELECT * FROM car_reservation")
    suspend fun getAllReservations(): List<CarReservationEntity>

    @Query("SELECT car_reservation.id, car_reservation.end_reservation, car_reservation.start_reservation, car_reservation.status_reservation, car_reservation.days_reservation, car_reservation.full_price, car_info.name, car_info.brand, car_reservation.id_car_foreign, car_reservation.id_user_foreign FROM car_reservation JOIN car_info ON car_reservation.id_car_foreign = car_info.id")
    fun getReservationItems(): Flow<List<ReservationsTurple>>

    @Query("DELETE FROM car_reservation WHERE id = :id")
    fun deleteReservationById(id: Long)
}