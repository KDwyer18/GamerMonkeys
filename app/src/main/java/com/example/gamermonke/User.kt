package com.example.gamermonke

import androidx.room.*

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "fname") val firstName: String?,
    @ColumnInfo(name = "lname") val lastName: String?,
    @ColumnInfo(name = "bmr") val BMR: Double?,
    @ColumnInfo(name = "feet") val hFeet: Int?,
    @ColumnInfo(name = "inches") val hInches: Int?,
    @ColumnInfo(name = "sex") val sex: String?,
    @ColumnInfo(name = "age") val age: Int?,
    @ColumnInfo(name = "activity") val activityLevel: String?,
    @ColumnInfo(name = "location") val location: String?
)

