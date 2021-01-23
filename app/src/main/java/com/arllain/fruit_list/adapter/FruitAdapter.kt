package com.arllain.fruit_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.R
import com.arllain.fruit_list.model.Fruit
import com.arllain.fruit_list.utils.convertToBitmap
import com.arllain.fruit_list.viewholder.FruitViewHolder

class FruitAdapter(private val listener: FruitViewHolder.OnItemClickListener) : RecyclerView.Adapter<FruitViewHolder>() {

    private var fruitList: List<Fruit> = ArrayList<Fruit>();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_layout,
            parent, false
        )

        return FruitViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        val currentItem = fruitList[position]

        holder.fruitImage.setImageBitmap(currentItem.imageBase64?.convertToBitmap())
        holder.fruitName.text = (currentItem.name)
        if (currentItem.benefits?.length!! > 80){
            holder.fruitBenefits.text = currentItem.benefits!!.substring(0, 80) + " ..."
        }else {
            holder.fruitBenefits.text = (currentItem.benefits)
        }
    }

    fun setProductList(fruits: List<Fruit>) {
        fruits.also { this.fruitList = it }
        notifyDataSetChanged()
    }

    override fun getItemCount() = fruitList.size
}