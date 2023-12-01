package com.example.ecstore.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date



@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val nome: String,
    val cpf: String,
    val email: String,
    val registerDate: Date = Calendar.getInstance().time,
    val active_yn: Boolean = true
) {
}