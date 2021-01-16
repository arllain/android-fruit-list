package com.arllain.fruit_list

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.adapter.FruitAdapter
import com.arllain.fruit_list.model.Fruit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val fruitList = generateDummyList(100)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = FruitAdapter(fruitList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    private fun generateDummyList(size: Int): List<Fruit> {
        val fruitList = ArrayList<Fruit>()

        for (i in 0 until size){
            val drawable = when (i % 3 ) {
                0 -> R.drawable.ic_banana
                1 -> R.drawable.ic_mamao
                else -> R.drawable.ic_morango
            }

            val n = i + 1
            val item = Fruit(drawable, "Fruit $n", "Benefits $n")
            fruitList += item
        }

        return  fruitList
    }

}