package com.arllain.fruit_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.arllain.fruit_list.databinding.ActivityViewFruitBinding
import com.arllain.fruit_list.model.Fruit

class ViewFruitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewFruitBinding
    private var fruit: Fruit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        setupDeleteButton();
    }

    private fun setUpView() {
        fruit = intent.getParcelableExtra<Fruit>(MainActivity.MAIN_ACTIVITY_FRUIT_EXTRA_ID)
        binding.tvFruitName.text = fruit?.name
        val drawable = when (fruit?.imageResource ) {
            0 -> R.drawable.ic_banana
            1 -> R.drawable.ic_mamao
            else -> R.drawable.ic_morango
        }

        binding.fruitImageView.setImageResource(drawable)
        binding.fruitBenefits.text = fruit?.benefits
    }

    private fun setupDeleteButton() {
        binding.btDeleteFruit.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra(MainActivity.FRUIT_TO_DELETE, fruit)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}