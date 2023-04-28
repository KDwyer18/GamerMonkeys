package com.example.gamermonke

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(userDao: UserDao) {

    // This LiveData object that is notified when we've fetched the weather
    val data = MutableLiveData<UserData>()

    // This flow is triggered when any change happens to the database
    val allUsers: Flow<List<UserData>> = userDao.getUsers()

    var name = ""
    var location = ""
    var feet = 0
    var inches = 0
    var sex = ""
    var activityLevel = ""
    var age = 0
    var weight = 0.0
    var bmr = 0.0

    private var mUserDao: UserDao = userDao

    @WorkerThread
     fun insert(userInfo: UserData) {
        if (name != null) {
//            mUserDao.insert(User( name, bmr, feet, inches, sex, age, activityLevel, location, weight))
            mUserDao.insert(userInfo)
        }
    }

    @WorkerThread
    fun getUsers(): Flow<List<UserData>> {
        return mUserDao.getUsers()
    }

    @WorkerThread
    fun getNames(): List<String> {
        return mUserDao.getUsersName()
    }

    @WorkerThread
    fun getName(name: String): UserData {
        return mUserDao.getUser(name)
    }

    // Make the repository singleton. Could in theory
    // make this an object class, but the companion object approach
    // is nicer (imo)
    companion object {
        private var mInstance: UserRepository? = null
        private lateinit var mScope: CoroutineScope
        @Synchronized
        fun getInstance(weatherDao: UserDao,
                        scope: CoroutineScope
        ): UserRepository {
            mScope = scope
            return mInstance?: synchronized(this){
                val instance = UserRepository(weatherDao)
                mInstance = instance
                instance
            }
        }
    }

}