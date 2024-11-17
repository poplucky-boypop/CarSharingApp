package com.example.carsharingapp

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.data.UserLoginEntity
import com.example.carsharingapp.data.UserSingInTuple
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var userLoginPassword: EditText
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etLoginMail)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<android.widget.Button>(R.id.btnLogin)

        val db = UserDatabase.getDatabase(this)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            lifecycleScope.launch {
                val userSingInTuple = db.getUserDao().findByEmail(email)
                if (userSingInTuple != null && userSingInTuple.password == password) {
                    saveAuthToken(userSingInTuple.id.toString())
                    val intent = Intent(this@LoginActivity, ProgramMenuActivity::class.java)
                    startActivity(intent)
                }
            }
            /*Thread{
                val userSingInTuple = db.getUserDao().findByEmail(email)
            }.start()
            if (userSingInTuple != null && userSingInTuple.password == password) {
                saveAuthToken(userSingInTuple.id.toString())
                val intent = Intent(this, ProgramMenuActivity::class.java)
                startActivity(intent)
            }*/
        }


        val tvNewRegistration: TextView = findViewById(R.id.tvNewRegistration)
        userLoginPassword = findViewById(R.id.etLoginPassword)

        tvNewRegistration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        userLoginPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (userLoginPassword.right - userLoginPassword.compoundDrawablePadding - userLoginPassword.compoundDrawables[2].bounds.width())) {
                    togglePasswordVisibility(userLoginPassword)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility(editText: EditText) {
        if (isPasswordVisible) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        isPasswordVisible = !isPasswordVisible
        //editText.setSelection(editText.text.length) // Устанавливаем курсор в конец текста
    }

    private fun saveAuthToken(token: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }
}