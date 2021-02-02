package com.arllain.fruit_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.R
import com.arllain.fruit_list.model.Fruit
import com.arllain.fruit_list.utils.convertToBitmap
import com.arllain.fruit_list.viewholder.FruitViewHolder

class FruitAdapter(private val listener: FruitViewHolder.OnItemClickListener) : RecyclerView.Adapter<FruitViewHolder>(), Filterable {

    private var fruitList: List<Fruit> = ArrayList<Fruit>();
    private var fruitListFiltered: List<Fruit> = ArrayList<Fruit>();

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
        fruits.also { this.fruitListFiltered = it }
        notifyDataSetChanged()
    }

    override fun getItemCount() = fruitList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charsequence: CharSequence?): FilterResults {

                val filterResults = FilterResults()
                if(charsequence == null || charsequence.length < 0) {
                    filterResults.count = fruitListFiltered.size
                    filterResults.values = fruitListFiltered
                }else{
                    val searchChr = charsequence.toString().toLowerCase()

                    val fruitsFiltered = ArrayList<Fruit>()

                    for (fruit in fruitListFiltered) {
                        if((fruit.name?.contains(searchChr) == true) || (fruit.benefits?.contains(searchChr) == true)){
                            fruitsFiltered.add(fruit)
                        }
                    }

                    filterResults.count = fruitsFiltered.size
                    filterResults.values = fruitsFiltered
                }

                return  filterResults
            }

            override fun publishResults(charsequence: CharSequence?, filterResults: FilterResults?) {
                fruitList = filterResults!!.values as ArrayList<Fruit>
                notifyDataSetChanged()
            }

        }

    }

}