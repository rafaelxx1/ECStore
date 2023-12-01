package com.example.ecstore.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ecstore.model.Produto

@Dao
interface ProductDao {

    @Query("SELECT * FROM Produto")
    fun findAll() : List<Produto>

    @Query("SELECT * FROM Produto WHERE id IN (:id)")
    fun findById(id: Long?) : Produto

    @Insert
    fun save(produto: Produto)

    @Delete
    fun delete(produto: Produto)

    @Update
    fun update(produto: Produto)
}