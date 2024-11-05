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

        btnNext.setOnClickListener {
            val driverLicense = userDriverLicense.text.toString()
            val dateIssue = userDateIssue.text.toString()

            if (driverLicense != "" && dateIssue != ""){
                val intent = Intent(this, SuccessRegistrationActivity::class.java)
                startActivity(intent)
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


}