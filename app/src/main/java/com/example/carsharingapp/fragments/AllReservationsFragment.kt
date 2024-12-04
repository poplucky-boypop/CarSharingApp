package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsharingapp.R
import com.example.carsharingapp.adapters.RecyclerReservationCarsAdapter
import com.example.carsharingapp.data.CarReservationEntity
import com.example.carsharingapp.data.ReservationsTurple
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.data.UserViewModel
import com.example.carsharingapp.databinding.FragmentAllReservationsBinding
import kotlinx.coroutines.launch

class AllReservationsFragment : Fragment() {

    private var _binding: FragmentAllReservationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var adapter: RecyclerReservationCarsAdapter
    private lateinit var mList: List<ReservationsTurple>
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_all_reservations, container, false)
        _binding = FragmentAllReservationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvRecyclerAllReservations)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val db = UserDatabase.getDatabase(requireContext())
        userViewModel = ViewModelProvider(requireActivity(), UserViewModel.Factory(db.getUserDao()))[UserViewModel::class.java]
        adapter = RecyclerReservationCarsAdapter(emptyList())
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            //mList = db.getUserDao().getReservationItems()
            //adapter = RecyclerReservationCarsAdapter(mList)
            userViewModel.getAllReservation().observe(viewLifecycleOwner) { mList ->
                adapter.updateReservation(mList)
            }

            //recyclerView.adapter = adapter
            adapter.onButtonClick = { reservation ->
                val fragmentReservationProfile = ReservationProfileFragment()
                val bundle2 = Bundle()
                bundle2.putParcelable("reservation", reservation)


                fragmentReservationProfile.arguments = bundle2

                // Заменить текущий фрагмент на FragmentB
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_reservation, fragmentReservationProfile)
                    .addToBackStack(null)
                    .commit()
            }
        }

        val ibReturnBackAllReservations = view.findViewById<ImageButton>(R.id.ibReturnBackAllReservations)

        ibReturnBackAllReservations.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}