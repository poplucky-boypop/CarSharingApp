package com.example.carsharingapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.lifecycleScope
import com.example.carsharingapp.MainActivity
import com.example.carsharingapp.R
import com.example.carsharingapp.data.UserDatabase
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvProfileFIO : TextView = view.findViewById(R.id.tvProfileFIO)
        val tvProfileEmail : TextView = view.findViewById(R.id.tvProfileEmail)
        val tvProfileGender : TextView = view.findViewById(R.id.tvProfileGender)
        val authToken = getAuthToken()?.toLongOrNull()

        val db = UserDatabase.getDatabase(requireContext())
        lifecycleScope.launch {
            val userData = db.getUserDao().getById(authToken?: 0)
            tvProfileFIO.setText(userData.firstName + " " + userData.secondName)
            tvProfileEmail.setText(userData.email)
            tvProfileGender.setText(userData.gender)
        }

        val btnExitAccount = view.findViewById<TextView>(R.id.tvExitAccount)

        btnExitAccount.setOnClickListener{
            val intent = Intent(requireContext(), MainActivity::class.java)
            deleteAuthToken()
            startActivity(intent)
            requireActivity().finish()
        }
    }

    fun getAuthToken(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }

    fun deleteAuthToken() {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("auth_token") // Удаляет запись с ключом "auth_token"
        editor.apply() // Сохраняет изменения
    }
}