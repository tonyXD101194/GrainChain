package com.example.grainchaintest.ui.activities

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Chronometer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import com.example.grainchaintest.R
import com.example.grainchaintest.interfaces.SaveRouteInterface
import com.example.grainchaintest.ui.dialogs.SaveRouteDialog
import com.example.grainchaintest.ui.fragments.MapContainerFragment
import com.example.grainchaintest.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SaveRouteInterface {

    var isRecording = false
    private lateinit var detailViewModel : DetailViewModel

    private var countTimer = "00:00"

    companion object {

        private const val TAG_DIALOG_SAVE = "DialogUser"
        private const val MY_PERMISSIONS_REQUEST_ACCESS = 1
    }

    private val mapFragment: MapContainerFragment by lazy {

        MapContainerFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_main)

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        activityMainChronometer.base = SystemClock.elapsedRealtime()

        this.setContentFragment(mapFragment)

        this.initListeners()

        this.requestPermissions()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        this.menuInflater.inflate(R.menu.menu_action,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.item_list -> DetailActivity.startActivity(this)
        }

        return true
    }

    override fun onSaveRoute(name: String) {

        detailViewModel.insertRoute(
            user = name,
            time = countTimer,
            list = mapFragment.listPosition)

        //Clean list to new record
        mapFragment.listPosition = ArrayList()
    }

    private fun setContentFragment(fragment: Fragment) {

        this.supportFragmentManager.commit {
            replace(R.id.activityMainMapContainer,fragment)
        }
    }


    private fun initListeners() {

        activityMainStartRecordFloatingActionButton.setOnClickListener {

            if (isRecording) {

                isRecording = false

                activityMainStartRecordFloatingActionButton.setImageResource(R.drawable.ic_play_arrow)

                activityMainDescriptionTextView.text = resources.getString(R.string.click_start_record)

                countTimer = activityMainChronometer.text.toString()

                activityMainChronometer.stop()

                activityMainChronometer.base = SystemClock.elapsedRealtime()

                activityMainChronometer.visibility = View.INVISIBLE

                this.showDialogSaveRoute()
            } else {

                activityMainChronometer.start()

                isRecording = true

                activityMainStartRecordFloatingActionButton.setImageResource(R.drawable.ic_stop)

                activityMainDescriptionTextView.text = resources.getString(R.string.click_started_record)

                activityMainChronometer.visibility = View.VISIBLE

            }

        }
    }

    private fun requestPermissions() {

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_ACCESS)
    }

    private fun showDialogSaveRoute() {

        val dialog : SaveRouteDialog = SaveRouteDialog.newInstance(this)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager,TAG_DIALOG_SAVE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        mapFragment.getCurrentLocation()

    }

}
