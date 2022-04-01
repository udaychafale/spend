package com.example.spends.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SpendDao {

    @Insert
    suspend fun addSpend(spend: Spend)

    @Query("SELECT * FROM spends")
    suspend fun getLast20Spends(): List<Spend>

}