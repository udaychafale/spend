package com.example.spends.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "spends")
data class Spend(
    val amount: String,
    val description: String,
    val img: String

) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}