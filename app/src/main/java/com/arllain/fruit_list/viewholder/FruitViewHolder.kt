package com.arllain.fruit_list.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.R
import com.arllain.fruit_list.model.Fruit


class FruitViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    val fruitImage: ImageView = itemView.findViewById(R.id.fruit_image_view)
    val fruitName: TextView = itemView.findViewById(R.id.fruit_name)
    val fruitBenefits: TextView = itemView.findViewById(R.id.fruit_benefits)
    val listener = listener

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}