package com.example.carsharingapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsharingapp.R

class ImageSwipeAdapter(private val imagePaths: List<String>) : RecyclerView.Adapter<ImageSwipeAdapter.ImageSwipeViewHolder>() {
    class ImageSwipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivCarProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSwipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_car_profile, parent, false)
        return ImageSwipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imagePaths.size
    }

    override fun onBindViewHolder(holder: ImageSwipeViewHolder, position: Int) {
        val imagePath = imagePaths[position]
        holder.imageView.setImageURI(Uri.parse(imagePath)) // Устанавливаем изображение из URI
    }
}