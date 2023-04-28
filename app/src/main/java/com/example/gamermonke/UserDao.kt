package com.example.gamermonke

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getUsers(): Flow<List<UserData>>
    @Query("SELECT name From user_table")
    fun getUsersName(): List<String>

    @Query("SELECT * FROM user_table WHERE name = :username LIMIT 1")
    fun getUser(username: String): UserData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserData)

}