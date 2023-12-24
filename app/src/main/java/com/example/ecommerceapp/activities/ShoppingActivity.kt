package com.example.ecommerceapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {


    private  lateinit var binding: ActivityShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)
    }
}