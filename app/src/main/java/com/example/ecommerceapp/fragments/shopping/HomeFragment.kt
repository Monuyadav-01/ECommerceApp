package com.example.ecommerceapp.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerceapp.adapters.HomeViewPagerAdapter
import com.example.ecommerceapp.databinding.FragmentHomeBinding
import com.example.ecommerceapp.fragments.categories.AccessoryFragment
import com.example.ecommerceapp.fragments.categories.ChairFragment
import com.example.ecommerceapp.fragments.categories.CupboardFragment
import com.example.ecommerceapp.fragments.categories.FurnitureFragment
import com.example.ecommerceapp.fragments.categories.MainCategoryFragment
import com.example.ecommerceapp.fragments.categories.TableFragment
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoriesFragment = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment(),
        )

        val viewPagerAdapter =
            HomeViewPagerAdapter(categoriesFragment, childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Chair"
                2 -> tab.text = "CupBoard"
                3 -> tab.text = "Accessory"
                4 -> tab.text = "Furniture"

            }
        }.attach()


    }


}