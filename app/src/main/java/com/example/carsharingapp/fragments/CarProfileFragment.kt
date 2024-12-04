package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.carsharingapp.ProgramMenuActivity
import com.example.carsharingapp.R
import com.example.carsharingapp.data.CarBookmarksEntity
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.data.UserLoginEntity
import com.example.carsharingapp.data.UserViewModel
import com.example.carsharingapp.databinding.FragmentCarProfileBinding
import com.example.carsharingapp.databinding.FragmentSettingsBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class CarProfileFragment : Fragment() {

    private var _binding: FragmentCarProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var imageBookmarks: ImageButton
    lateinit var imageColorBookmark: ImageButton
    var isBookmark: CarBookmarksEntity? = null
    private lateinit var userViewModel: UserViewModel
    val tabLayout = getActivity()?.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tlProgramMenu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_car_profile, container, false)
        _binding = FragmentCarProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carInfo = arguments?.getParcelable<CarInfo>("car_info")

        imageBookmarks = view.findViewById(R.id.ibBookmark)
        imageColorBookmark = view.findViewById(R.id.ibColorBookmark)
        val db = UserDatabase.getDatabase(requireContext())
        val authToken = getAuthToken()?.toLongOrNull()

        userViewModel = ViewModelProvider(
            requireActivity(),
            UserViewModel.Factory(db.getUserDao())
        )[UserViewModel::class.java]

        // Используем данные carInfo
        carInfo?.let {
            binding.tvNameCarProfile.setText("${it.brand} ${it.name}")
            lifecycleScope.launch {
                if (authToken != null) {
                    isBookmark = db.getUserDao().findCarInBookmarksById(authToken, it.id)
                    if (isBookmark != null){
                        imageBookmarks.visibility = View.GONE
                        imageColorBookmark.visibility = View.VISIBLE
                        }
                }
            }
        }
        //if (isBookmark != null) {
        //    imageBookmarks.visibility = View.GONE
        //}

        imageBookmarks.setOnClickListener {
            val bookmark = CarBookmarksEntity(
                null,
                authToken?: 1000,
                carInfo?.id?: 1000
            )
            userViewModel.addBookmark(bookmark)
            /*Thread{
                db.getUserDao().addBookmark(bookmark)
            }.start()*/

            imageBookmarks.visibility = View.GONE
            imageColorBookmark.visibility = View.VISIBLE

        }

        imageColorBookmark.setOnClickListener {
            Thread{
                if (authToken != null && carInfo != null) {
                    db.getUserDao().deleteBookmark(authToken, carInfo.id)
                }
            }.start()
            imageBookmarks.visibility = View.VISIBLE
            imageColorBookmark.visibility = View.GONE
        }

        val btnNext = view.findViewById<android.widget.Button>(R.id.btnStartRegistrationLease)

        btnNext.setOnClickListener{
            val fragmentRegistrationLease = RegistrationLeaseFragment()
            val bundle3 = Bundle()
            bundle3.putParcelable("car_info", carInfo)


            fragmentRegistrationLease.arguments = bundle3
            /*if (tabLayout != null) {
                tabLayout.visibility = View.GONE
            }*/
            //(activity as? ProgramMenuActivity)?.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tlProgramMenu)?.visibility = View.GONE

            // Заменить текущий фрагмент на FragmentB
            parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container_car_profile, fragmentRegistrationLease)
                .addToBackStack(null)
                .commit()
        }

        val btnBack = view.findViewById<ImageButton>(R.id.ibReturnBackCarProfile)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getAuthToken(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }

    override fun onResume() {
        super.onResume()
        (activity as? ProgramMenuActivity)?.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tlProgramMenu)?.visibility = View.VISIBLE
    }
}