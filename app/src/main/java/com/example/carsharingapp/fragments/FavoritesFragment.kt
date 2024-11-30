package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsharingapp.R
import com.example.carsharingapp.adapters.RecyclerCarsAdapter
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.data.UserViewModel
import com.example.carsharingapp.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.launch


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var adapter: RecyclerCarsAdapter
    private lateinit var mList: List<CarInfo>
    private lateinit var listBookmarks: List<Long>
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_favorites, container, false)
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvRecyclerCarsBookmarks)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = UserDatabase.getDatabase(requireContext())
        val authToken = getAuthToken()?.toLongOrNull()

        userViewModel = ViewModelProvider(requireActivity(), UserViewModel.Factory(db.getUserDao()))[UserViewModel::class.java] //this -> requireActivity()

        adapter = RecyclerCarsAdapter(emptyList())
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            listBookmarks = authToken?.let { db.getUserDao().findBookmarksByIdUser(it) }!!
            //mList = db.getUserDao().findCarsInBookmarks(listBookmarks)
            userViewModel.getCarsInBookmarks(listBookmarks).observe(viewLifecycleOwner) { cars ->
                adapter.updateCars(cars)
            }
            //adapter = RecyclerCarsAdapter(mList)
            //recyclerView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    fun getAuthToken(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }

}