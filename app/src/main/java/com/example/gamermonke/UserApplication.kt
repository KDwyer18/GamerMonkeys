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

//
//class LifestyleApplication : Application() {
//
//    val applicationScope = CoroutineScope(SupervisorJob())
//
//    // Inject scope and application context into singleton database
//    val database by lazy{ LifestyleRoomDatabase.getDatabase(this, applicationScope) }
//
//    val repository by lazy{ LifestyleRepository.getInstance(database.userDao(), applicationScope) }
//}