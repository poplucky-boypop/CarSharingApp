package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.carsharingapp.MainActivity
import com.example.carsharingapp.ProgramMenuActivity
import com.example.carsharingapp.R
import com.example.carsharingapp.databinding.FragmentSettingsBinding

//const val ARG_OBJECT = "object"

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Найдите элемент "Тема"
        val themeSection = view.findViewById<RelativeLayout>(R.id.rlProfile)

        // Установите обработчик нажатия
        themeSection.setOnClickListener {
            // Замените фрагмент на ThemeFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment()) // Убедитесь, что fragment_container — это ID контейнера, где размещается фрагмент
                .addToBackStack(null) // Добавьте транзакцию в back stack для возврата
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}