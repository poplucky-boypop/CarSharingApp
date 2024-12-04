package com.example.carsharingapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserSingInTuple (
    val id: Long,
    val password: String
)

data class BookmarksTurple (
    val idCar: Long
)

@Parcelize
data class ReservationsTurple (
    val id: Long,
    val start_reservation: String,
    val end_reservation: String,
    val status_reservation: String,
    val days_reservation: String,
    val full_price: String,
    val name: String,
    val brand: String,
    val id_car_foreign: Long,
    val id_user_foreign: Long
) : Parcelable