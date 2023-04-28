package com.example.gamermonke

import android.app.Application
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy{ MonkeyDatabase.getDatabase(this, applicationScope)}

    val repository by lazy {UserRepository.getInstance(database.userDao(),applicationScope)}
}