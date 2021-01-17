package com.arllain.fruit_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.adapter.FruitAdapter
import com.arllain.fruit_list.model.Fruit
import com.arllain.fruit_list.viewholder.FruitViewHolder
import kotlin.random.Random

class MainActivity : AppCompatActivity(), FruitViewHolder.OnItemClickListener {

    companion object {
        const val MAIN_ACTIVITY_FRUIT_EXTRA_ID = "fruit"
    }

    private var  fruitList = generateDummyList(5)
    private val adapter = FruitAdapter(fruitList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val addFruitIntent = Intent(this, AddFruitActivity::class.java)
            startActivity(addFruitIntent)
//            insertItem(view)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    private fun insertItem(view: View) {
        val newFruit = Fruit(R.drawable.ic_banana, "new fruit added", "beneficios")
        fruitList.add(newFruit)
        adapter.notifyItemInserted(fruitList.size)
    }

    private fun removeItem(view: View){
        val index: Int = Random.nextInt(8)
        fruitList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    private fun generateDummyList(size: Int): ArrayList<Fruit> {
        var fruitList = ArrayList<Fruit>()
        for (i in 0 until size){
            val drawable = when (i % 3 ) {
                0 -> R.drawable.ic_banana
                1 -> R.drawable.ic_mamao
                else -> R.drawable.ic_morango
            }

            val n = i + 1
            val item = Fruit(drawable, "Fruit $n", "Lorem Ipsum is simply dummy " +
                    "text of the printing and typesetting industry. Lorem Ipsum has been the " +
                    "industry's standard dummy text ever since the 1500s, when an unknown printer " +
                    "took a galley of type and scrambled it to make a type specimen book. " +
                    "It has survived not only five centuries, but also the leap into electronic " +
                    "typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
                    "with the release of Letraset sheets containing Lorem Ipsum passages, and more " +
                    "recently with desktop publishing software like Aldus PageMaker including " +
                    "versions of Lorem Ipsum.")
            fruitList.add(item)
        }

        return  fruitList
    }

    override fun onItemClick(position: Int) {
        val fruit = fruitList[position]
        val viewFruitIntent = Intent(this, ViewFruitActivity::class.java)
        viewFruitIntent.putExtra( MAIN_ACTIVITY_FRUIT_EXTRA_ID, fruit)
        startActivity(viewFruitIntent)
    }

}