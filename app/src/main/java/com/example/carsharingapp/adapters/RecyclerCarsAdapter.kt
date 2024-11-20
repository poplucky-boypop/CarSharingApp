package com.example.carsharingapp.adapters

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsharingapp.R
import com.example.carsharingapp.data.CarInfo

class RecyclerCarsAdapter(var mList: List<CarInfo>) :
    RecyclerView.Adapter<RecyclerCarsAdapter.RecyclerCarsViewHolder>() {
    inner class RecyclerCarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCar: ImageView = itemView.findViewById(R.id.ivCarMers)
        val nameCar: TextView = itemView.findViewById(R.id.tvNameCar)
        val brandCar: TextView = itemView.findViewById(R.id.tvBrandCar)
        val priceCar: TextView = itemView.findViewById(R.id.tvPriceCar)
        val transmissionCar: TextView = itemView.findViewById(R.id.tvTransmissionCar)
        val fuelCar: TextView = itemView.findViewById(R.id.tvFuelCar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerCarsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return RecyclerCarsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
        //return 1
    }

    override fun onBindViewHolder(holder: RecyclerCarsViewHolder, position: Int) {
        //holder.imageCar.setImageResource(mList[position].logo)
        holder.nameCar.setText(mList[position].name)
        holder.brandCar.setText(mList[position].brand)
        holder.priceCar.setText(mList[position].price)
        holder.transmissionCar.setText(mList[position].transmission)
        holder.fuelCar.setText(mList[position].fuel)
    }
}