package com.example.gamermonke

import androidx.annotation.NonNull
import androidx.room.*

@Entity
data class User(
    @PrimaryKey @NonNull val fullName: String,
    @ColumnInfo(name = "bmr") var BMR: Double?,
    @ColumnInfo(name = "feet") var hFeet: Int?,
    @ColumnInfo(name = "inches") var hInches: Int?,
    @ColumnInfo(name = "sex") var sex: String?,
    @ColumnInfo(name = "age") var age: Int?,
    @ColumnInfo(name = "activity") var activityLevel: String?,
    @ColumnInfo(name = "location") var location: String?,
    @ColumnInfo(name = "weight") var weight: Double?
)

