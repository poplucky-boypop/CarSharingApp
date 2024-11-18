package com.example.carsharingapp.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.carsharingapp.MainActivity
import com.example.carsharingapp.OnboardingActivity
import com.example.carsharingapp.ProgramMenuActivity
import com.example.carsharingapp.R

class SearchCarsActivityFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_cars_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val query = arguments?.getString("search_query")
        Handler(Looper.getMainLooper()).postDelayed({
            //parentFragmentManager.popBackStack()
            query?.let {
                val bundle = Bundle()
                bundle.putString("search_query", it)

                val fragmentC = SearchFilteredCarsFragment()
                fragmentC.arguments = bundle

                // Заменить текущий фрагмент на FragmentB
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_home_page, fragmentC)
                    .addToBackStack(null)
                    .commit()
            }
        }, 2000)
        super.onViewCreated(view, savedInstanceState)
    }
}