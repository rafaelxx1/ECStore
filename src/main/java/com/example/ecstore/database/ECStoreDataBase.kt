package com.example.ecstore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecstore.database.converter.Converter
import com.example.ecstore.database.dao.ProductDao
import com.example.ecstore.database.dao.ProductUserRefDao
import com.example.ecstore.database.dao.UserDao
import com.example.ecstore.model.ProductUserRef
import com.example.ecstore.model.Produto
import com.example.ecstore.model.User

@Database(entities = [Produto::class, User::class, ProductUserRef::class], version = 1)
@TypeConverters(Converter::class)
abstract class ECStoreDataBase : RoomDatabase(){
    abstract fun ProductDao(): ProductDao
    abstract fun UserDao(): UserDao
    abstract fun ProductUserRefDao(): ProductUserRefDao

    companion object {
        fun getInstance(context: Context): ECStoreDataBase{
            return Room.databaseBuilder(
                context,
                ECStoreDataBase::class.java,
                "ecstore.db"
            ).allowMainThreadQueries().build()
        }
    }

}