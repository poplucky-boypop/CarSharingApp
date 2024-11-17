package com.example.carsharingapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
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
import java.util.Calendar

class RegistrationDriverActivity : AppCompatActivity() {

    lateinit var userDateIssue : EditText
    private val calendar = Calendar.getInstance()
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_driver)

        val ibReturnBack: ImageButton = findViewById(R.id.ibReturnBack)

        ibReturnBack.setOnClickListener {
            finish()
        }

        val btnNext: TextView = findViewById(R.id.btnNext)
        val userDriverLicense: EditText = findViewById(R.id.etDriverLicense)
        userDateIssue = findViewById(R.id.etDateIssue)

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val surname = intent.getStringExtra("surname")
        val name = intent.getStringExtra("name")
        val midllename = intent.getStringExtra("midllename")
        val dateBirth = intent.getStringExtra("dateBirth")
        val gender = intent.getStringExtra("gender")

        //userDriverLicense.setText(email)

        btnNext.setOnClickListener {
            val driverLicense = userDriverLicense.text.toString()
            val dateIssue = userDateIssue.text.toString()

            val db = UserDatabase.getDatabase(this)

            if (driverLicense != "" && dateIssue != ""){
                val userLogin = UserLoginEntity(
                    null,
                    email?: "",
                    password?: "",
                    surname?: "",
                    name?: "",
                    midllename?: "",
                    dateBirth?: "",
                    gender?: "",
                    driverLicense,
                    dateIssue
                )
                Thread{
                    db.getUserDao().addUser(userLogin)
                }.start()
                lifecycleScope.launch {
                    val userSingInTuple = db.getUserDao().findByEmail(email?:" ")
                    if (userSingInTuple != null && userSingInTuple.password == password) {
                        saveAuthToken(userSingInTuple.id.toString())
                        val intent = Intent(this@RegistrationDriverActivity, SuccessRegistrationActivity::class.java)
                        startActivity(intent)
                    }
                }

            }

        }

        userDateIssue.setOnClickListener{
            showDatePicker(userDateIssue)
        }
    }


    private fun showDatePicker(editText: EditText){
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        datePickerDialog = DatePickerDialog(this, {_, year, month, day ->
            val selectedDate = "$day/${month+1}/$year"
            editText.setText(selectedDate)
        }, year, month, day)
        datePickerDialog.show()
    }

    fun saveAuthToken(token: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }


}