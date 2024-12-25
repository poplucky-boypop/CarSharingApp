package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.carsharingapp.R


class BecomeHostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_become_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnStartAddCar = view.findViewById<android.widget.Button>(R.id.btnStartAddCar)
        val btnBackBecomeHost = view.findViewById<ImageButton>(R.id.ibBackBecomeHost)

        btnStartAddCar.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_settings, AddCarFragment())
                .addToBackStack(null)
                .commit()
        }

        btnBackBecomeHost.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}