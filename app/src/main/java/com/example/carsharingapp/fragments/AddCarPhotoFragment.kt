package com.example.carsharingapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carsharingapp.R
import com.example.carsharingapp.adapters.ChooseImagesAdapter
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.databinding.FragmentAddCarPhotoBinding

class AddCarPhotoFragment : Fragment() {

    private val selectedImages = mutableListOf<Uri>()
    private val maxImages = 5

    private val selectImagesLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris != null) {
            selectedImages.clear()
            uris.take(maxImages).forEach { uri ->
                // Запрашиваем постоянное разрешение
                requireContext().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                selectedImages.add(uri) // Сохраняем URI как строку
            }
            //selectedImages.addAll(uris.take(maxImages))
            updateUI()
        }
    }

    private var _binding: FragmentAddCarPhotoBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_car_photo, container, false)
        _binding = FragmentAddCarPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var carInfo = arguments?.getParcelable<CarInfo>("car_info")
        val db = UserDatabase.getDatabase(requireContext())

        binding.rvSmallImages.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.ibChooseImages.setOnClickListener {
            selectImagesLauncher.launch("image/*")
        }

        binding.btnEndAddCarPhoto.setOnClickListener {
            val paths = selectedImages.map { it.toString() }
            if (carInfo != null) {
                carInfo.imagePaths = paths
            }
            Thread {
                if (carInfo != null) {
                    db.getUserDao().addCar(carInfo)
                }
            }.start()

            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            //parentFragmentManager.popBackStack()
            parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container_settings, EndAddCarFragment())
                .commit()
        }

        binding.ibBackAddPCarPhoto.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun updateUI() {
        if (selectedImages.isNotEmpty()) {
            binding.ivMainImage.visibility = View.VISIBLE
            binding.ibChooseImages.visibility = View.GONE
            binding.ivMainImage.setImageURI(selectedImages[0])
        }
        val imagesForRecyclerView = selectedImages.drop(1)
        val adapter = ChooseImagesAdapter(imagesForRecyclerView) { selectedIndex ->
            // Обработка замены главной картинки
            val clickedIndex = selectedIndex + 1
            val temp = selectedImages[0]
            selectedImages[0] = selectedImages[clickedIndex]
            selectedImages[clickedIndex] = temp
            updateUI()
        }
        binding.rvSmallImages.adapter = adapter
    }
}