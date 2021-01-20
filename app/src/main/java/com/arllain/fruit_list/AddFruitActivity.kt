package com.arllain.fruit_list

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.arllain.fruit_list.databinding.ActivityAddFruitBinding
import com.arllain.fruit_list.model.Fruit
import com.arllain.fruit_list.utils.convertToBase64
import java.io.FileNotFoundException
import java.io.InputStream


class AddFruitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFruitBinding
    private var imageUri: Uri? = null
    private val pickImage = 100
    private var fruit = Fruit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupInsertButton();
        setUpPhotoAddButton();
    }

    private fun setUpPhotoAddButton() {
        binding.fabUploadPhoto.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.imgFruit.setImageURI(imageUri)
            try {
                setFruitImage(data?.data)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun setupInsertButton() {
        binding.btAddFruit.setOnClickListener {
            val name = binding.edtFruitName.text.toString()
            val benefits = binding.edtFruitBenefit.text.toString()
            if (isFieldsValidated(name, benefits)) {
                fruit.name = name
                fruit.benefits = benefits
                binding.edtFruitName.text.clear()
                binding.edtFruitBenefit.text.clear()


                val returnIntent = Intent()
                returnIntent.putExtra(MainActivity.FRUIT_TO_ADD, fruit)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }

    private fun setFruitImage(imageUri: Uri?) {
        val imageStream: InputStream? =
                imageUri?.let { contentResolver.openInputStream(it) }
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        binding.imgFruit.setImageResource(android.R.color.transparent)
        binding.imgFruit.setImageBitmap(selectedImage)
        fruit.imageBase64 = selectedImage.convertToBase64()
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