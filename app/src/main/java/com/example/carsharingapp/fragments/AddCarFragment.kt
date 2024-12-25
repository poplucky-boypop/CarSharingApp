package com.example.carsharingapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.example.carsharingapp.R

class AddCarFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etLocateAddCar = view.findViewById<EditText>(R.id.etLocateAddCar)
        val btnNextAddCar = view.findViewById<android.widget.Button>(R.id.btnNextAddCar)
        val ibBackAddCar = view.findViewById<ImageButton>(R.id.ibBackAddCar)

        etLocateAddCar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val currentText = p0.toString()
                if (p0 != "") { btnNextAddCar.visibility = View.VISIBLE }
                else { btnNextAddCar.visibility = View.GONE }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        btnNextAddCar.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("locate", etLocateAddCar.text.toString())

            val fragment = AddCarInfoFragment()
            fragment.arguments = bundle

            // Заменить текущий фрагмент на FragmentB
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_settings, fragment)
                .addToBackStack(null)
                .commit()
        }

        ibBackAddCar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}