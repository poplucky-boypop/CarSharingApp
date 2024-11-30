package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.carsharingapp.R
import com.example.carsharingapp.data.CarBookmarksEntity
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.data.UserLoginEntity
import com.example.carsharingapp.data.UserViewModel
import com.example.carsharingapp.databinding.FragmentCarProfileBinding
import com.example.carsharingapp.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class CarProfileFragment : Fragment() {

    private var _binding: FragmentCarProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var imageBookmarks: ImageButton
    var isBookmark: CarBookmarksEntity? = null
    private lateinit var userViewModel: UserViewModel

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
        val db = UserDatabase.getDatabase(requireContext())
        val authToken = getAuthToken()?.toLongOrNull()

        userViewModel = ViewModelProvider(
            requireActivity(),
            UserViewModel.Factory(db.getUserDao())
        )[UserViewModel::class.java]

        // Используем данные carInfo
        carInfo?.let {
            binding.tvNameCarProfile.text = it.name
            lifecycleScope.launch {
                if (authToken != null) {
                    isBookmark = db.getUserDao().findCarInBookmarksById(authToken, it.id)
                    if (isBookmark != null){
                        imageBookmarks.visibility = View.GONE
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
}