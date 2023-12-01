package com.example.ecstore.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.math.BigDecimal

@Entity
data class Produto(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val nome: String,
    val price: BigDecimal,
    val img: String?,
    val type: ProductType = ProductType.NONE,
    val active_yn: Boolean = true
) {

}
