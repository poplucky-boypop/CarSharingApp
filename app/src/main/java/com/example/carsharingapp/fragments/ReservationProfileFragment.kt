package com.example.carsharingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.carsharingapp.R
import com.example.carsharingapp.data.ReservationsTurple
import com.example.carsharingapp.data.UserDatabase
import kotlinx.coroutines.launch

class ReservationProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reservation = arguments?.getParcelable<ReservationsTurple>("reservation")
        val db = UserDatabase.getDatabase(requireContext())

        val tvTitleReservationProfile = view.findViewById<TextView>(R.id.tvTitleReservationProfile)
        val tvNameCarReservationProfile = view.findViewById<TextView>(R.id.tvNameCarReservationProfile)
        val tvBrandCarReservationProfile = view.findViewById<TextView>(R.id.tvBrandCarReservationProfile)
        val tvPriceCarReservationProfile = view.findViewById<TextView>(R.id.tvPriceCarReservationProfile)
        val tvStartRes = view.findViewById<TextView>(R.id.tvStartReservationProfile)
        val tvEndRes = view.findViewById<TextView>(R.id.tvEndReservationProfile)
        val tvFIOReservationProfile = view.findViewById<TextView>(R.id.tvFIOReservationProfile)
        val tvLicenceReservationProfile = view.findViewById<TextView>(R.id.tvLicenceReservationProfile)
        val tvStatusReservationProfile = view.findViewById<TextView>(R.id.tvStatusReservationProfile)
        val tvCountDayRentProfile = view.findViewById<TextView>(R.id.tvCountDayRentProfile)
        val tvPriceReservationProfile = view.findViewById<TextView>(R.id.tvPriceReservationProfile)
        val tvCountDaySaveProfile = view.findViewById<TextView>(R.id.tvCountDaySaveProfile)
        val tvStrahReservationProfile = view.findViewById<TextView>(R.id.tvStrahReservationProfile)
        val tvFullPriceReservationProfile = view.findViewById<TextView>(R.id.tvFullPriceReservationProfile)

        val btnDeleteReservationProfile = view.findViewById<android.widget.Button>(R.id.btnDeleteReservationProfile)
        val ibReturnBackReservationProfile = view.findViewById<ImageButton>(R.id.ibReturnBackReservationProfile)


        if (reservation != null) {
            tvTitleReservationProfile.setText("Бронирование #${reservation.id}")
            tvStartRes.setText("08:00, ${reservation.start_reservation}")
            tvEndRes.setText("08:00, ${reservation.end_reservation}")
            tvStatusReservationProfile.setText("${reservation.status_reservation}")
            tvCountDayRentProfile.setText("x${reservation.days_reservation} дней")
            tvCountDaySaveProfile.setText("x${reservation.days_reservation} дней")
            tvStrahReservationProfile.setText("${reservation.days_reservation.toLong() * 100}Р")
            tvFullPriceReservationProfile.setText("${reservation.full_price}Р")
        }

        lifecycleScope.launch {
            val carInfo = reservation?.let { db.getUserDao().getCarById(it.id_car_foreign) }
            val userInfo = reservation?.let { db.getUserDao().getById(it.id_user_foreign) }
            if (userInfo != null && carInfo != null) {
                tvFIOReservationProfile.setText("${userInfo.secondName} ${userInfo.firstName}")
                tvLicenceReservationProfile.setText("${userInfo.driverLicense}")
                tvNameCarReservationProfile.setText("${carInfo.name}")
                tvBrandCarReservationProfile.setText("${carInfo.brand}")
                tvPriceCarReservationProfile.setText("${carInfo.price}Р")
                tvPriceReservationProfile.setText("${carInfo.price.toLong() * reservation.days_reservation.toLong()}Р")
            }
        }

        ibReturnBackReservationProfile.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnDeleteReservationProfile.setOnClickListener {
            Thread{
                if (reservation != null) {
                    db.getUserDao().deleteReservationById(reservation.id)
                }
            }.start()
            parentFragmentManager.popBackStack()
        }
    }
}