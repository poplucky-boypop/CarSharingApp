package com.example.carsharingapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsharingapp.CarProfileActivity
import com.example.carsharingapp.ProgramMenuActivity
import com.example.carsharingapp.R
import com.example.carsharingapp.adapters.RecyclerCarsAdapter
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.databinding.FragmentHomePageBinding
import kotlinx.coroutines.launch


class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: RecyclerCarsAdapter
    private lateinit var mList: List<CarInfo>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_page, container, false)
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvRecyclerCars)
        searchView = view.findViewById(R.id.svSearchCar)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = UserDatabase.getDatabase(requireContext())
        lifecycleScope.launch {
            mList = db.getUserDao().getAllCars()
            adapter = RecyclerCarsAdapter(mList)
            //recyclerView.post {
                //recyclerView.adapter = adapter
                //recyclerView.requestLayout()
            //}
            recyclerView.adapter = adapter
            adapter.onButtonClickCarProfile = { carInfo ->
                val fragmentCarProfile = CarProfileFragment()
                val bundle2 = Bundle()
                bundle2.putParcelable("car_info", carInfo)


                fragmentCarProfile.arguments = bundle2

                // Заменить текущий фрагмент на FragmentB
                /*parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_home_page, fragmentCarProfile)
                    .addToBackStack(null)
                    .commit()*/
                val intent = Intent(requireContext(), CarProfileActivity::class.java).apply {
                    putExtra("data_key", carInfo)
                    putExtra("boolean_key", false)
                }
                startActivity(intent)
            }
            adapter.onButtonClickRegistrationReservation = { carInfo ->
                val fragmentRegistrationLease = RegistrationLeaseFragment()
                val bundle5 = Bundle()
                bundle5.putParcelable("car_info", carInfo)


                fragmentRegistrationLease.arguments = bundle5

                // Заменить текущий фрагмент на FragmentB
                /*parentFragmentManager.beginTransaction()
                    //.replace(R.id.fragment_container_home_page, fragmentRegistrationLease)
                    .replace(R.id.fragment_container_home_page, CarProfileFragment())
                    //.replace(R.id.fragment_container_car_profile, fragmentRegistrationLease)
                    .addToBackStack(null)
                    .commit()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_car_profile, fragmentRegistrationLease)
                    .addToBackStack(null)
                    .commit()*/
                val intent = Intent(requireContext(), CarProfileActivity::class.java).apply {
                    putExtra("data_key", carInfo)
                    putExtra("boolean_key", true)
                }
                startActivity(intent)
            }
        }

        /*searchView.setOnClickListener {
            searchView.isIconified = false
        }*/

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val bundle = Bundle()
                    bundle.putString("search_query", it)

                    val fragmentB = SearchCarsActivityFragment()
                    fragmentB.arguments = bundle

                    // Заменить текущий фрагмент на FragmentB
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_home_page, fragmentB)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        //searchView.setOnSearchClickListener(SearchView.OnSuggestionListener)
    }

    override fun onResume() {
        super.onResume()

        //recyclerView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        recyclerView.requestLayout()
        binding.root.requestLayout()
        (activity as? ProgramMenuActivity)?.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tlProgramMenu)?.visibility = View.VISIBLE
    }


}