package com.example.carsharingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.data.UserLoginEntity
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {

    lateinit var userPassword : EditText
    lateinit var userRepeatPassword : EditText
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        val ibReturnBack: ImageButton = findViewById(R.id.ibReturnBack)

        ibReturnBack.setOnClickListener {
            finish()
        }

        val btnNext: TextView = findViewById(R.id.btnNext)
        val userEmail: EditText = findViewById(R.id.etNewMail)
        userPassword = findViewById(R.id.etNewPassword)
        userRepeatPassword = findViewById(R.id.etRepeatPassword)
        val acceptRules: CheckBox = findViewById(R.id.cbAcceptRules)

        btnNext.setOnClickListener {
            val email = userEmail.text.toString()
            val password = userPassword.text.toString()
            val repeatPassword = userRepeatPassword.text.toString()

            val db = UserDatabase.getDatabase(this)

            if (email != "" && password != "" && repeatPassword == password && acceptRules.isChecked()) {
                lifecycleScope.launch {
                    val userSingInTuple = db.getUserDao().findByEmail(email)
                    if (userSingInTuple != null) {
                        // Сделать, что такой email уже есть
                    } else {
                        val intent = Intent(this@RegistrationActivity, RegistrationInfoActivity::class.java)

                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        startActivity(intent)
                    }
                }

                /*val userLogin = UserLoginEntity(
                    null,
                    email,
                    password
                )
                Thread{
                    db.getUserDao().addUser(userLogin)
                }.start()*/

                /*val userSingInTuple = db.getUserDao().findByEmail(email)
                //val intent = Intent(this, RegistrationInfoActivity::class.java)
                if (userSingInTuple != null) {
                    saveAuthToken(userSingInTuple.id.toString())
                }*/
                //val intent = Intent(this, ProgramMenuActivity::class.java)
                //startActivity(intent)
            }
        }
        userPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (userPassword.right - userPassword.compoundDrawablePadding - userPassword.compoundDrawables[2].bounds.width())) {
                    togglePasswordVisibility(userPassword)
                    return@setOnTouchListener true
                }
            }
            false
        }

        userRepeatPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (userRepeatPassword.right - userRepeatPassword.compoundDrawablePadding - userRepeatPassword.compoundDrawables[2].bounds.width())) {
                    togglePasswordVisibility(userRepeatPassword)
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
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        isPasswordVisible = !isPasswordVisible
        //editText.setSelection(editText.text.length) // Устанавливаем курсор в конец текста
    }

    fun saveAuthToken(token: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }
}