package com.example.grainchaintest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route")
data class RouteModel (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "user")
    var user: String,

    @ColumnInfo(name = "distance")
    var distance: Double,

    @ColumnInfo(name = "time_route")
    var time: String
)
