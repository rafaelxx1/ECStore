package com.example.ecstore.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Produto::class,
            parentColumns = ["id"],
            childColumns = ["produtoId"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index(value = ["produtoId"], unique = true),
        Index(value = ["userId"], unique = true)
    ]
)
data class ProductUserRef(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val produtoId: Long
)