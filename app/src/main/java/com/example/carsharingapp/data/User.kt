package com.example.carsharingapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "user_info_table")
data class UserInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "second_name") val secondName: String,
    @ColumnInfo(name = "midlle_name") val midlleName: String,
    val dateBirth: String,
    val gender: String,
    @ColumnInfo(name = "driver_license") val driverLicense: String,
    @ColumnInfo(name = "date_issue") val dateIssue: String
)


@Entity(
    tableName = "user_login_table",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = UserInfo::class,
            parentColumns = ["id"],
            childColumns = ["info_id"]
        )
    ]
)
data class UserLogin (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val password: String,
    @ColumnInfo(name = "info_id") val infoId: Int
)

@Entity(tableName = "car_info")
data class CarInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val brand: String,
    val price: Int,
    val transmission: String,
    val fuel: String,
    val description: String
)