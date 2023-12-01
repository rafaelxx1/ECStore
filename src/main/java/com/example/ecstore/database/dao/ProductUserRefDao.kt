package com.example.ecstore.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ecstore.model.ProductUserRef

@Dao
interface ProductUserRefDao {
    @Query("SELECT * FROM ProductUserRef")
    fun findAll() : List<ProductUserRef>

    @Query("SELECT * FROM ProductUserRef WHERE userId IN (:id)")
    fun findById(id: Long?) : ProductUserRef

    @Insert
    fun save(value: ProductUserRef)

    @Delete
    fun delete(value: ProductUserRef)
}