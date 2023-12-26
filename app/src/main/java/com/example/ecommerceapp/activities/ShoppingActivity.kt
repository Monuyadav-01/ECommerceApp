package com.example.ecommerceapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.R.*
import com.example.ecommerceapp.databinding.ActivityShoppingBinding


class ShoppingActivity : AppCompatActivity() {


    private  lateinit var binding: ActivityShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(id.shoppingHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)

    }
}
