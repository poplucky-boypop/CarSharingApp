package com.example.carsharingapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistrationInfoActivity : AppCompatActivity() {

    lateinit var userDateBirth : EditText
    private val calendar = Calendar.getInstance()
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarion_info)


        val ibReturnBack: ImageButton = findViewById(R.id.ibReturnBack)

        ibReturnBack.setOnClickListener {
            finish()
        }

        val btnNext: TextView = findViewById(R.id.btnNext)
        val userSurname: EditText = findViewById(R.id.etSurname)
        val userName: EditText = findViewById(R.id.etName)
        val userMidllename: EditText = findViewById(R.id.etMidllename)
        userDateBirth = findViewById<EditText>(R.id.etDateBirth)
        val male : RadioButton = findViewById(R.id.rbMale)

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        //userSurname.setText(email)


        //userDateBirth.setOnClickListener

        btnNext.setOnClickListener {
            val surname = userSurname.text.toString()
            val name = userName.text.toString()
            val midllename = userMidllename.text.toString()
            val dateBirth = userDateBirth.text.toString()

            if (surname != "" && name != "" && midllename != "" && dateBirth != ""){
                val intent = Intent(this, RegistrationDriverActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                intent.putExtra("surname", surname)
                intent.putExtra("name", name)
                intent.putExtra("midllename", midllename)
                intent.putExtra("dateBirth", dateBirth)
                if (male.isChecked) { intent.putExtra("gender", "М") }
                else { intent.putExtra("gender", "Ж") }
                startActivity(intent)
            }

        }

        userDateBirth.setOnClickListener {
            showDatePicker(userDateBirth)
        }
    }

//    val datePickerDialog = DatePickerDialog(this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
//        val selectedDate = Calendar.getInstance()
//        selectedDate.set(year, monthOfYear, dayOfMonth)
//        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        val formattedDate = dateFormat.format(selectedDate.time)
//        userDateBirth.setText(formattedDate)

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