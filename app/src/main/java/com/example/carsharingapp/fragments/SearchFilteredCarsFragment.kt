package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsharingapp.R
import com.example.carsharingapp.adapters.RecyclerCarsAdapter
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.databinding.FragmentHomePageBinding
import com.example.carsharingapp.databinding.FragmentSearchFilteredCarsBinding
import kotlinx.coroutines.launch


class SearchFilteredCarsFragment : Fragment() {

    private var _binding: FragmentSearchFilteredCarsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView //= view.findViewById(R.id.rvRecyclerFilteredCars)
    private lateinit var adapter: RecyclerCarsAdapter
    private lateinit var mList: List<CarInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search_filtered_cars, container, false)
        _binding = FragmentSearchFilteredCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString("search_query")

        recyclerView = view.findViewById(R.id.rvRecyclerFilteredCars)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = UserDatabase.getDatabase(requireContext())
        lifecycleScope.launch {
            mList = db.getUserDao().getCarsByName(query.toString())
            adapter = RecyclerCarsAdapter(mList)
            recyclerView.post {
                recyclerView.adapter = adapter
                //recyclerView.requestLayout()
            }
            recyclerView.requestLayout()
            //recyclerView.adapter = adapter
        }

        val buttonToFirst = view.findViewById<ImageButton>(R.id.ibReturnBackFilteredCars)
        buttonToFirst.setOnClickListener {
            // Очищаем весь стек и возвращаемся на FragmentA
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            val fragmentA = HomePageFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_home_page, fragmentA)
                .commit()
        }
        //val textView = view?.findViewById<TextView>(R.id.tvTest)
        //if (textView != null) {
        //    textView.text = "Результаты поиска для: $query"
        //}
    }
    override fun onResume() {
        super.onResume()

        //recyclerView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        recyclerView.requestLayout()
        binding.root.requestLayout()
    }

}