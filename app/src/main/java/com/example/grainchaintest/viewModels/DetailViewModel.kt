package com.example.grainchaintest.viewModels

import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import com.example.grainchaintest.models.DetailModel
import com.example.grainchaintest.models.PositionsModel
import com.example.grainchaintest.models.RouteModel
import com.example.grainchaintest.repository.DetailRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var repository : DetailRepository = DetailRepository(application)

    private var detailData = MutableLiveData<List<DetailModel>>()

    private var itemDelete = MutableLiveData<DetailModel>()

    var details : LiveData<List<DetailModel>> = detailData.map { it }

    var itemDeleted : LiveData<DetailModel> = itemDelete.map { it }

    fun insertRoute(user : String, time : String, list: List<LatLng>) {
        this.viewModelScope.launch {
            withContext(Dispatchers.IO) {


                val distance = getDistance(list)

                repository.insertRoute(RouteModel(
                    user = user,
                    distance = distance,
                    time = time))

                val id = repository.getIdRoute()

                list.forEach {
                    repository.insertPosition(PositionsModel(
                        id_pos = id,
                        lat = it.latitude,
                        lng = it.longitude))
                }
            }

        }
    }

    fun deleteRoute(model : DetailModel) {

        this.viewModelScope.launch {
            withContext(Dispatchers.IO) {

                repository.deleteRoute(model.model)

                itemDelete.postValue(model)
            }
        }
    }


    fun parseDetailModel() {

        this.viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val routes = repository.getRoutes()

                if (routes.isNotEmpty()) {

                    val listModel : List<DetailModel> = ArrayList()

                    routes.forEach {

                        val listLat : List<LatLng> = ArrayList()

                        val list = repository.getPositions(it.id)

                        if (list.isNotEmpty()) {

                            list.forEachIndexed { _, model ->

                                (listLat as ArrayList).add(LatLng(model.lat,model.lng))
                            }
                        }

                        (listModel as ArrayList).add(DetailModel(it, listLat))
                    }

                    detailData.postValue(listModel)
                }
            }
        }
    }

    private fun getDistance(list : List<LatLng>) : Double {

        var distance = 0.0

        list.forEachIndexed { index, latLng ->

            val distanceFloat = FloatArray(2)

            if (list.size > index + 1) {
                Location.distanceBetween(latLng.latitude,latLng.longitude,list[index + 1].latitude, list[index + 1].longitude, distanceFloat)

                distance += distanceFloat[0]
            }
        }

        return distance

    }


}