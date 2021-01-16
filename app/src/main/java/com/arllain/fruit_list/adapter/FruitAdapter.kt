package com.arllain.fruit_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.R
import com.arllain.fruit_list.model.Fruit
import com.arllain.fruit_list.viewholder.FruitViewHolder

class FruitAdapter(private val fruitList: List<Fruit>) : RecyclerView.Adapter<FruitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_layout,
            parent, false
        )

        return FruitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        val currentItem = fruitList[position]

        holder.fruitImage.setImageResource(currentItem.imageResource)
        holder.fruitName.text = (currentItem.name)
        holder.fruitBenefits.text = (currentItem.benefits)
    }

    override fun getItemCount() = fruitList.size
}