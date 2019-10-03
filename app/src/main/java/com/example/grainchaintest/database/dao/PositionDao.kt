package com.example.grainchaintest.database.dao

import androidx.room.*
import com.example.grainchaintest.models.PositionsModel

@Dao
interface PositionDao {

    @Insert
    fun insert(position : PositionsModel)

    @Query("SELECT * FROM positions WHERE pos = :id")
    fun getPositions(id : Int) : List<PositionsModel>

    @Delete
    fun delete(position : PositionsModel)
}