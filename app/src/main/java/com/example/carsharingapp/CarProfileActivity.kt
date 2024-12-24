package com.example.carsharingapp

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.fragments.CarProfileFragment
import com.example.carsharingapp.fragments.RegistrationLeaseFragment

class CarProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_car_profile)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val myData = intent.getParcelableExtra<CarInfo>("data_key")
        val myBoolean = intent.getBooleanExtra("boolean_key", false)



        if (myBoolean) {
            val fragmentRegistrationLease = RegistrationLeaseFragment()
            val bundle = Bundle()
            bundle.putParcelable("car_info", myData)
            fragmentRegistrationLease.arguments = bundle
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_car_profile_activity, fragmentRegistrationLease)
                //.addToBackStack(null)
                .commit()
        } else {
            val fragmentCarProfile = CarProfileFragment()
            val bundle = Bundle()
            bundle.putParcelable("car_info", myData)
            fragmentCarProfile.arguments = bundle
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_car_profile_activity, fragmentCarProfile)
                //.addToBackStack(null)
                .commit()
        }
    }
}