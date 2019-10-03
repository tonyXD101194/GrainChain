package com.example.grainchaintest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.grainchaintest.database.dao.PositionDao
import com.example.grainchaintest.database.dao.RouteDao
import com.example.grainchaintest.models.PositionsModel
import com.example.grainchaintest.models.RouteModel

@Database(entities = [RouteModel::class, PositionsModel::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {

    abstract val daoRoute : RouteDao
    abstract val daoPosition : PositionDao

    companion object {
        @Volatile
        var INSTANCE: DatabaseHelper? = null

        val TABLE_NAME = "route_db"

        fun getDatabase(context : Context) : DatabaseHelper{

            if (INSTANCE == null){

                synchronized(DatabaseHelper::class.java){
                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DatabaseHelper::class.java,
                            TABLE_NAME
                        ).build()
                    }
                }
            }

            return INSTANCE!!
        }
    }

}