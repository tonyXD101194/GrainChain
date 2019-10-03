package com.example.grainchaintest.database.dao

import androidx.room.*
import com.example.grainchaintest.models.RouteModel

@Dao
interface RouteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(route : RouteModel)

    @Delete
    fun delete(route : RouteModel) : Int

    @Query("SELECT id FROM route ORDER BY id DESC LIMIT 1")
    fun getLastRoute() : Int

    @Query("SELECT * FROM route")
    fun getRoutes() : List<RouteModel>
}