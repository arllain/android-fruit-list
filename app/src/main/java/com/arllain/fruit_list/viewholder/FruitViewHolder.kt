package com.arllain.fruit_list.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.R

class FruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val fruitImage: ImageView = itemView.findViewById(R.id.fruit_image_view)
    val fruitName: TextView = itemView.findViewById(R.id.fruit_name)
    val fruitBenefits: TextView = itemView.findViewById(R.id.fruit_benefits)
}