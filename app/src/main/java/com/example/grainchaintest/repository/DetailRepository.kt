package com.example.grainchaintest.repository

import android.app.Application
import com.example.grainchaintest.database.DatabaseHelper
import com.example.grainchaintest.database.dao.PositionDao
import com.example.grainchaintest.database.dao.RouteDao
import com.example.grainchaintest.models.PositionsModel
import com.example.grainchaintest.models.RouteModel

class DetailRepository(application: Application) {

    private var daoRoute : RouteDao
    private var positionDao : PositionDao

    init {

        val dbHelper = DatabaseHelper.getDatabase(application)

        this.daoRoute = dbHelper.daoRoute
        this.positionDao = dbHelper.daoPosition
    }


    //For Routes
    fun insertRoute(route : RouteModel){

        daoRoute.insert(route)
    }

    fun deleteRoute(route : RouteModel) : Int{

        return daoRoute.delete(route)
    }

    fun getIdRoute() : Int {

        return daoRoute.getLastRoute()
    }

    fun getRoutes() : List<RouteModel> {

        return daoRoute.getRoutes()
    }

    //For positions
    fun insertPosition(position : PositionsModel){

        positionDao.insert(position)
    }

    fun deletePosition(position: PositionsModel){

        positionDao.delete(position)
    }

    fun getPositions(id : Int) : List<PositionsModel> {

        return positionDao.getPositions(id)
    }

}