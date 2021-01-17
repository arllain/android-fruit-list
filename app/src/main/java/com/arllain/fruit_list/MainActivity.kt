package com.arllain.fruit_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.adapter.FruitAdapter
import com.arllain.fruit_list.model.Fruit
import com.arllain.fruit_list.viewholder.FruitViewHolder

class MainActivity : AppCompatActivity(), FruitViewHolder.OnItemClickListener {

    companion object {
        const val MAIN_ACTIVITY_FRUIT_EXTRA_ID = "fruit"
        const val MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE = 0
        const val MAIN_ACTIVITY_DETAILS_REQUEST_CODE  = 1;
        const val FRUIT_TO_ADD = "fruit_to_add"
        const val FRUIT_TO_DELETE = "fruit_to_delete"
    }

    private var  fruitList = generateDummyList(3)
    private val adapter = FruitAdapter(fruitList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        setupAddButton();
        setupRecyclerView()
    }

    private fun setupAddButton() {
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val addFruitIntent = Intent(this, AddFruitActivity::class.java)
            startActivityForResult(addFruitIntent, MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE) {
                addFruit(data)
            } else if (requestCode == MAIN_ACTIVITY_DETAILS_REQUEST_CODE ) {
                removeFruit(data)
            }
        }
    }

    private fun addFruit(data: Intent?) {
        data?.extras?.get(FRUIT_TO_ADD)?.let {
            fruitList.add(it as Fruit)
            adapter.notifyItemInserted(fruitList.lastIndex)
        }
    }

    private fun removeFruit(data: Intent?){
        data?.extras?.get(FRUIT_TO_DELETE)?.let {
            val positionToUpdate = fruitList.indexOf(it)
            fruitList.remove(it)
            adapter.notifyItemRemoved(positionToUpdate)
        }
    }

    override fun onItemClick(position: Int) {
        val fruit = fruitList[position]
        val viewFruitIntent = Intent(this@MainActivity, ViewFruitActivity::class.java)
        viewFruitIntent.putExtra( MAIN_ACTIVITY_FRUIT_EXTRA_ID, fruit)
        startActivityForResult(viewFruitIntent, MAIN_ACTIVITY_DETAILS_REQUEST_CODE)
    }

    private fun generateDummyList(size: Int): ArrayList<Fruit> {
        var fruitList = ArrayList<Fruit>()
        for (i in 0 until size){
            val drawable = when (i) {
                0 -> R.drawable.ic_banana
                1 -> R.drawable.ic_mamao
                else -> R.drawable.ic_morango
            }
            val fruitName = when(i) {
                0 -> "Banana"
                1 -> "Mamão"
                else -> "Morango"
            }

            val fruitBenefits = when(i) {
                0 -> "A banana é uma das frutas mais populares do Brasil e encontrada facilmente" +
                        " em todo território nacional. De origem asiática, ela se adaptou " +
                        "rapidamente ao clima do país e é um alimento bastante saudável, versátil" +
                        " e com preço acessível"
                1 -> "O mamão possui uma boa quantidade de nutrientes essenciais para a saúde. " +
                        "É rico em sais minerais, como Cálcio, Fósforo, Ferro, Sódio e Potássio, " +
                        "que participam na formação de ossos, dentes e sangue. O fruto também " +
                        "contém quantidades significativas de vitaminas A e C."
                else -> "O morango é também uma fruta rica em antioxidantes, como antocianinas e " +
                        "o ácido elágico, que conferem outros benefícios para a saúde, tias como " +
                        "combater o envelhecimento da pele, ajudar a prevenir doenças " +
                        "cardiovasculares, melhorar a capacidade mental, prevenir o câncer " +
                        "e ajudar a combater inflamações"
            }

            val item = Fruit(drawable, "$fruitName", fruitBenefits)
            fruitList.add(item)
        }

        return  fruitList
    }
}