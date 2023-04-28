package com.example.gamermonke

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 1)
abstract class MonkeyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    // Make the db singleton. Could in theory
    // make this an object class, but the companion object approach
    // is nicer (imo)
    companion object {
        @Volatile
        private var mInstance: MonkeyDatabase? = null
        fun getDatabase(
                context: Context,
                scope : CoroutineScope
        ): MonkeyDatabase {
            return mInstance?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MonkeyDatabase::class.java, "weather.db"
                )
                        .addCallback(RoomDatabaseCallback(scope))
                        .fallbackToDestructiveMigration()
                        .build()
                mInstance = instance
                instance
            }
        }

        private class RoomDatabaseCallback(
                private val scope: CoroutineScope
        ): RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                mInstance?.let { database ->
                    scope.launch(Dispatchers.IO){
                        populateDbTask(database.userDao())
                    }
                }
            }
        }

        suspend fun populateDbTask (userDao: UserDao) {
            userDao.insert(User("superguy", 1000.0, 6, 6, "male", 40, "intermediate", "slc, utah", 150.0))
        }
    }
}