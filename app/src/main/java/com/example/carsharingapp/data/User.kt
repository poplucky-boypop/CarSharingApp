package com.example.carsharingapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/*@Entity(tableName = "user_info_table")
data class UserInfo (
    @PrimaryKey
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "second_name") val secondName: String,
    @ColumnInfo(name = "midlle_name") val midlleName: String,
    val dateBirth: String,
    val gender: String,
    @ColumnInfo(name = "driver_license") val driverLicense: String,
    @ColumnInfo(name = "date_issue") val dateIssue: String,
    @ColumnInfo(name = "photo_profile_url") val photoProfileUrl: String,
    @ColumnInfo(name = "photo_license_url") val photoLicenseUrl: String,
    @ColumnInfo(name = "photo_issue_url") val photoIssueUrl: String,
)*/


@Entity(
    tableName = "user_login_table",
    indices = [
        Index("email", unique = true)
    ]
    /*indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = UserInfo::class,
            parentColumns = ["id"],
            childColumns = ["info_id"]
        )
    ]*/
)
data class UserLoginEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val email: String,
    //val email: String,
    val password: String,
    //@ColumnInfo(name = "info_id") val infoId: Int
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "second_name") val secondName: String,
    @ColumnInfo(name = "midlle_name") val midlleName: String,
    val dateBirth: String,
    val gender: String,
    @ColumnInfo(name = "driver_license") val driverLicense: String,
    @ColumnInfo(name = "date_issue") val dateIssue: String
)

@Parcelize
@Entity(tableName = "car_info")
data class CarInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val brand: String,
    val price: String,
    val transmission: String,
    val fuel: String,
    val description: String
) : Parcelable


@Entity(tableName = "car_bookmarks")
data class CarBookmarksEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "id_user_foreign") val idUserForeign: Long,
    @ColumnInfo(name = "id_car_foreign") val idCarForeign: Long
)