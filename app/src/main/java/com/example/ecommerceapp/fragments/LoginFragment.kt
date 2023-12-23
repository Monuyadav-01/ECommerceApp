package com.example.ecommerceapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecommerceapp.activities.ShoppingActivity
import com.example.ecommerceapp.databinding.FragmentLoginBinding
import com.example.ecommerceapp.utils.Resource
import com.example.ecommerceapp.viewmodel.LoginViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            buttonLoginLogin.setOnClickListener {
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()

                viewModel.login(email, password)
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.buttonLoginLogin.startAnimation()
                    }

                    is Resource.Success -> {
                        binding.buttonLoginLogin.revertAnimation()

                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT)
                            .show()
                        binding.buttonLoginLogin.revertAnimation()
                    }

                    else -> {
                        Unit
                    }
                }
            }
        }
    }

}