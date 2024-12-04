package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.carsharingapp.R


class EndRegistrationReservationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end_registration_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEndRegistrationReservation = view.findViewById<android.widget.Button>(R.id.btnEndRegistrationReservation)
        val btnOpenReservationsEndReg = view.findViewById<TextView>(R.id.btnOpenReservationsEndReg)

        btnEndRegistrationReservation.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnOpenReservationsEndReg.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_car_profile, AllReservationsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}