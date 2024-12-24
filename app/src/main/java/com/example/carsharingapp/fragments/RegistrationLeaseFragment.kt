package com.example.carsharingapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.FragmentManager
import com.example.carsharingapp.R
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.data.CarReservationEntity
import com.example.carsharingapp.data.UserDatabase
import com.example.carsharingapp.data.UserLoginEntity
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar


class RegistrationLeaseFragment : Fragment() {

    private val calendar = Calendar.getInstance()
    private lateinit var datePickerDialog: DatePickerDialog
    var startResDate = "01.12.2024"
    var endResDate = "03.12.2024"
    var days: Long = 2
    var fullPrice: Long = 100
    lateinit var globalView: View
    lateinit var globalCarInfo: CarInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_lease, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        globalCarInfo = arguments?.getParcelable<CarInfo>("car_info")!!
        globalView = view

        val btnBack = view.findViewById<ImageButton>(R.id.ibReturnBackRegistrationLease)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val nameCarReservation = view.findViewById<TextView>(R.id.tvNameCarRegistrationLease)
        val brandCarReservation = view.findViewById<TextView>(R.id.tvBrandCarRegistrationLease)
        val priceCarReservation = view.findViewById<TextView>(R.id.tvPriceCarRegistrationLease)
        nameCarReservation.setText(globalCarInfo.name)
        brandCarReservation.setText(globalCarInfo.brand)
        priceCarReservation.setText(globalCarInfo.price)


        val startReservation = view.findViewById<TextView>(R.id.tvStartRegistrationReservation)
        val endReservation = view.findViewById<TextView>(R.id.tvEndRegistrationReservation)
        //val countDayRent = view.findViewById<TextView>(R.id.tvCountDayRent)
        val btnSetLease = view.findViewById<android.widget.Button>(R.id.btnSetLease)
        val db = UserDatabase.getDatabase(requireContext())
        val authToken = getAuthToken()?.toLongOrNull()

        startReservation.setText("08:00, ${startResDate}")
        endReservation.setText("08:00, ${endResDate}")
        //countDayRent.setText(days.toString())

        startReservation.setOnClickListener {
            showDatePicker(startReservation, true)
        }

        endReservation.setOnClickListener {
            showDatePicker(endReservation, false)
        }

        btnSetLease.setOnClickListener {
            if (days > 0) {
                //val fullPrice = globalCarInfo.price.toLongOrNull()?.times(days)
                val reservation = CarReservationEntity(
                    null,
                    globalCarInfo.id,
                    authToken ?: 1,
                    startResDate,
                    endResDate,
                    "Ожидание",
                    days,
                    fullPrice ?: 1000
                )
                Thread {
                    db.getUserDao().addReservation(reservation)
                }.start()
                //parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                parentFragmentManager.popBackStack()
                parentFragmentManager.beginTransaction()
                    //.add(R.id.fragment_container_car_profile, EndRegistrationReservationFragment())
                    .add(R.id.fragment_container_car_profile_activity, EndRegistrationReservationFragment())
                    //.replace(R.id.fragment_container_car_profile, EndRegistrationReservationFragment())
                    .commit()
            }

        }
        updateAllTextView()
    }

    private fun showDatePicker(textView: TextView, isStartDate: Boolean){
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        datePickerDialog = DatePickerDialog(requireContext(), {_, year, month, day ->
            var selectedDate = "08:00, $day.${month + 1}.$year"
            //startResDate = "08:00, $day.${month + 1}.$year"
            if(isStartDate) {
                startResDate = "$day.${month + 1}.$year"

            }
            else {endResDate = "$day.${month + 1}.$year"}

            if (1 <= day && day <= 9) {
                selectedDate = "08:00, 0$day.${month + 1}.$year"
                if(isStartDate) {startResDate = "0$day.${month + 1}.$year"}
                else {endResDate = "0$day.${month + 1}.$year"}
            }
            textView.setText(selectedDate)
            updateAllTextView()
        }, year, month, day)
        //datePickerDialog.datePicker.minDate
        datePickerDialog.show()
    }

    fun updateAllTextView() {
        val countDayRent1 = globalView.findViewById<TextView>(R.id.tvCountDayRent)
        val countDayRent2 = globalView.findViewById<TextView>(R.id.tvCountDaySave)
        val priceAllDayRent = globalView.findViewById<TextView>(R.id.tvPriceAllDayRent)
        val priceAllDaySave = globalView.findViewById<TextView>(R.id.tvPriceAllDaySave)
        val tvFullPrice = globalView.findViewById<TextView>(R.id.tvFullPrice)
        days = checkCountDays(startResDate, endResDate)
        countDayRent1.setText("x${days} дней")
        countDayRent2.setText("x${days} дней")
        val rentPrice = globalCarInfo.price.toLongOrNull()?.times(days)!!
        fullPrice = rentPrice + 100*days
        priceAllDayRent.setText("${rentPrice}")
        priceAllDaySave.setText("${100*days}")
        tvFullPrice.setText("${fullPrice}Р")
    }

    fun checkCountDays(startRes: String, endRes: String): Long {
        val dateFormatInput = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val startResFormat = LocalDate.parse(startRes, dateFormatInput)
        val endResFormat = LocalDate.parse(endRes, dateFormatInput)
        var days = ChronoUnit.DAYS.between(startResFormat, endResFormat)
        if (days < 0) days = 0
        return days
    }

    fun getAuthToken(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }
}