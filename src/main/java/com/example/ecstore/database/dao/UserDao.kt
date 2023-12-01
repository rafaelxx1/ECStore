package com.example.ecstore.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ecstore.model.User


@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun findAll() : List<User>

    @Query("SELECT * FROM User WHERE id IN (:id)")
    fun findById(id: Long?) : User

    @Insert
    fun save(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}