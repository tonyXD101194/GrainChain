package com.example.grainchaintest.ui.fragments

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.grainchaintest.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.grainchaintest.ui.activities.MainActivity
import com.example.grainchaintest.ui.dialogs.OneTextDialog
import com.google.android.gms.maps.model.Cap
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions


class MapContainerFragment : Fragment(), OnMapReadyCallback, LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private lateinit var mMap: GoogleMap
    private lateinit var mGoogleApiClient : GoogleApiClient
    private val TAG_ERROR_DIALOG = "TAG_ERROR_DIALOG"
    var listPosition : List<LatLng> = ArrayList()

    companion object {

        @JvmStatic
        fun newInstance() = MapContainerFragment()
    }

    override fun onStart() {
        super.onStart()

        mGoogleApiClient = GoogleApiClient.Builder(activity as Context)
                    .addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API!!).build()

        mGoogleApiClient.connect()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
    }

    //Api Cliente Callbacks

    override fun onConnected(p0: Bundle?) {

        getCurrentLocation()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

        this.showErrorDialog(activity!!.resources.getString(R.string.error_no_connected))
    }

    override fun onConnectionSuspended(p0: Int) {}

    //Location Callback

    override fun onLocationChanged(newLocation: Location?) {

        saveLocation(newLocation!!)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

    override fun onProviderEnabled(p0: String?) {}

    override fun onProviderDisabled(p0: String?) {}

    fun getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                activity as Context, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity as Context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {

            this.showErrorDialog(activity!!.resources.getString(R.string.error_permissions))

        } else {

            if (::mMap.isInitialized) {

                mMap.isMyLocationEnabled = true

                val manager : LocationManager = activity!!.getSystemService(LOCATION_SERVICE) as LocationManager

                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0.5f,this)

                var location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

                if (location != null) {
                    saveLocation(location)
                } else {
                    val providers = manager.getProviders(true)
                    location = null
                    for (provider in providers) {
                        val l : Location = manager.getLastKnownLocation(provider)
                        if (location == null || l.accuracy < location.getAccuracy()) {
                            location = l
                        }
                    }
                    if (location != null)
                        saveLocation(location)
                }
            }

        }
    }

    private fun saveLocation(location : Location) {

        //Toast.makeText(activity,"Position lat: ${location.latitude} long: ${location.longitude}",Toast.LENGTH_SHORT).show()

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude,location.longitude),15f))

        if ((activity as MainActivity).isRecording) {

            (listPosition as ArrayList).add(LatLng(location.latitude,location.longitude))
        }
    }

    private fun showErrorDialog(title : String) {

        val dialog : OneTextDialog = OneTextDialog.newInstance(title)
        dialog.isCancelable = false
        dialog.show(childFragmentManager,TAG_ERROR_DIALOG)
    }

}