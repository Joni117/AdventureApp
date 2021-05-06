package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class MainActivity : AppCompatActivity(), HikeListFragment.Callbacks {

   // private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bt_submit = findViewById<Button>(R.id.bt_submit)
        //  rBar = findViewById<RatingBar>(R.id.rBar)
       /* fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        findViewById<Button>(R.id.getPos).setOnClickListener {
            currentLocation()
        }*/
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = HikeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        if (ratingBar != null) {
            val button = findViewById<Button>(R.id.bt_submit)
            button?.setOnClickListener {
                //String rating = "Rating :: "+ ratingBar.getNumStars
                val msg: String = ratingBar.rating.toString()
                Toast.makeText(this@MainActivity, "Rating is: $msg", Toast.LENGTH_SHORT).show()
            }
        }
    }

   /* private fun currentLocation () {

        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        //val coordinates = Toast.LENGTH_SHORT

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                        .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if(it != null){
                Toast.makeText(applicationContext, "Lat:$it.latitude, Long:$it.longitude", Toast.LENGTH_SHORT).show()
            }
        }
    }
*/

    override fun onHikeSelected(hikeId: UUID) {
        val fragment = HikeFragment.newInstance(hikeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


}
