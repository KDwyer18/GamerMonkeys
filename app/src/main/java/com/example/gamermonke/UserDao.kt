package com.example.gamermonke

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<User>>
    @Query("SELECT fullName From user")
    fun getUsersName(): List<String>

    @Query("SELECT * FROM user WHERE fullName = :username LIMIT 1")
    fun getUser(username: String): User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

}