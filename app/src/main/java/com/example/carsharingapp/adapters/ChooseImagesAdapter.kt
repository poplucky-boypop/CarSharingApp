package com.example.carsharingapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsharingapp.R

class ChooseImagesAdapter(private val images: List<Uri>, private val onImageClick: (Int) -> Unit) :RecyclerView.Adapter<ChooseImagesAdapter.ChooseImagesViewHolder>() {
    inner class ChooseImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.ivImageItem)

        fun bind(uri: Uri) {
            imageView.setImageURI(uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseImagesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ChooseImagesViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ChooseImagesViewHolder, position: Int) {
        holder.bind(images[position])
        holder.itemView.setOnClickListener {
            onImageClick(position)
        }
    }
}