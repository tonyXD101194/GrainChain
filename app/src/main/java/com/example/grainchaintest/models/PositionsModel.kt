package com.example.grainchaintest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "positions")
data class PositionsModel (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "pos")
    var id_pos : Int = 0,

    @ColumnInfo(name = "latitude")
    var lat: Double = 0.0,

    @ColumnInfo(name = "second_cordinate")
    var lng : Double = 0.0

)