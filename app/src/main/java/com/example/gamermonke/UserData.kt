package com.example.gamermonke

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class UserData {
    @PrimaryKey
    var name: String = ""
    var location: String = ""
    var feet: String = ""
    var inches: String = ""
    var sexArr: String = ""
    var activityLevel: String = ""
    var age: String = ""
    var weight: String = ""
    var bmr: String = ""
    //var pfp: Bitmap? = null
}