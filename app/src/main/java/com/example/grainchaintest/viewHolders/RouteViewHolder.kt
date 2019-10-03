package com.example.grainchaintest.viewHolders

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grainchaintest.R
import com.example.grainchaintest.interfaces.ActionsRouteInterface
import com.example.grainchaintest.models.DetailModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.item_route.view.*
import java.text.DecimalFormat

class RouteViewHolder(
    container : View,
    private val context : Context,
    private val callback : ActionsRouteInterface) : RecyclerView.ViewHolder(container) {

    private var tvTitle : TextView = container.itemTitleTextView
    private var tvDistance : TextView = container.itemDistanceTextView
    private var tvTime : TextView = container.itemTimeTextView
    private var mapContainer : MapView = container.itemMapView
    private var btnShare : ImageView = container.itemShareImageView
    private var btnDelete : ImageView = container.itemDeleteImageView

    private val precision : DecimalFormat = DecimalFormat("#.##")

    private var distanceDescriptor = " m"


    fun bind(model : DetailModel){

        val distance = parseDistance(model.model.distance)

        val customDistance = "${precision.format(distance)} $distanceDescriptor"

        tvTitle.text = model.model.user

        tvDistance.text = String.format(
            context.resources.getString(
                R.string.distance_item),customDistance)

        tvTime.text = String.format(
            context.resources.getString(
                R.string.time_item),model.model.time.toString())

        with(mapContainer) {

            onCreate(null)

            getMapAsync {

                setMapLocation(it,model)
            }
        }

        btnShare.setOnClickListener {

            callback.onShare(model)
        }


        btnDelete.setOnClickListener {

            callback.onDelete(adapterPosition, model)
        }
    }

    private fun setMapLocation(googleMap: GoogleMap, model: DetailModel) {

        googleMap.uiSettings.isMapToolbarEnabled = false

        googleMap.addMarker(MarkerOptions().position(model.list[0]).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))

        googleMap.addMarker(MarkerOptions().position(model.list[model.list.size -1]).
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))

        val options = PolylineOptions().width(5f).color(Color.BLACK).geodesic(true)

        val group = LatLngBounds.Builder()

        model.list.forEach {
            options.add(it)

            group.include(it)
        }

        googleMap.addPolyline(options)

        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(group.build(), 100))

    }

    private fun parseDistance(distance : Double) : Double {

        var distanceParsed = 0.0

        if (distance > 0) {

            if (distance >= 1000) {

                distanceDescriptor = " km"

                distanceParsed = distance / 1000
            }
        }

        return distanceParsed

    }

}