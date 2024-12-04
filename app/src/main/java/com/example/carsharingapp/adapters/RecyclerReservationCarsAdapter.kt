package com.example.carsharingapp.adapters

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsharingapp.R
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.data.CarReservationEntity
import com.example.carsharingapp.data.ReservationsTurple

class RecyclerReservationCarsAdapter(var mList: List<ReservationsTurple>) :
    RecyclerView.Adapter<RecyclerReservationCarsAdapter.RecyclerReservationCarsViewHolder>() {

    var onButtonClick : ((ReservationsTurple) -> Unit)? = null

    inner class RecyclerReservationCarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namePlusBrandCar: TextView = itemView.findViewById(R.id.tvBrandPlusNameCarReservation)
        val startRes: TextView = itemView.findViewById(R.id.tvStartTimeReservation)
        val btnItem: com.google.android.material.card.MaterialCardView = itemView.findViewById(R.id.mcvItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerReservationCarsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        return RecyclerReservationCarsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerReservationCarsViewHolder, position: Int) {
        val reservation = mList[position]
        holder.namePlusBrandCar.setText(mList[position].name + " " +  mList[position].brand)
        holder.startRes.setText(mList[position].start_reservation)

        holder.btnItem.setOnClickListener {
            onButtonClick?.invoke(reservation)
        }
    }

    fun updateReservation(newReservation: List<ReservationsTurple>) {
        mList = newReservation
        notifyDataSetChanged() // Уведомление об изменении данных
    }
}