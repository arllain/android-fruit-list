package com.arllain.fruit_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arllain.fruit_list.databinding.ActivityAddFruitBinding
import com.arllain.fruit_list.model.Fruit

class AddFruitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFruitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupInsertButton();
    }

    private fun setupInsertButton() {
        binding.btAddFruit.setOnClickListener {
            val name = binding.edtFruitName.text.toString()
            val benefits = binding.edtFruitBenefit.text.toString()
            if (isFieldsValidated(name, benefits)) {
                val fruit = Fruit(0, name, benefits)
                binding.edtFruitName.text.clear()
                binding.edtFruitBenefit.text.clear()
                val returnIntent = Intent()
                returnIntent.putExtra(MainActivity.FRUIT_TO_ADD, fruit)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
    private fun isFieldsValidated(name: String, benefits: String): Boolean {
        var isValid = true
        when {
            binding.edtFruitName.text.toString().isEmpty() -> {
                binding.edtFruitName.error = "You must enter a fruit name"
                isValid = false
            }

            binding.edtFruitBenefit.text.toString().isEmpty() -> {
                binding.edtFruitBenefit.error = "You must enter the fruit's benefits"
                isValid = false
            }
        }

        return isValid;
    }
}