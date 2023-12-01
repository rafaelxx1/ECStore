package com.example.ecstore.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converter {

    @TypeConverter
    fun doubleToBigDecimal(value: Double?) : BigDecimal{
        return value?.let{ BigDecimal(value).setScale(2, RoundingMode.HALF_UP)} ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun bigDecimalToDouble(value: BigDecimal?) : Double? {
        return value?.setScale(2, RoundingMode.HALF_UP).let{ value?.toDouble()}

    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return date?.let {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateFormat.format(it)
        }
    }

    @TypeConverter
    fun stringToDate(value: String?): Date? {
        return value?.let {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateFormat.parse(it)
        }
    }
}