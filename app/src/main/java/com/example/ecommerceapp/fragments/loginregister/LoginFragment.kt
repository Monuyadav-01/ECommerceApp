package com.example.ecommerceapp.fragments.loginregister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.activities.ShoppingActivity
import com.example.ecommerceapp.databinding.FragmentLoginBinding
import com.example.ecommerceapp.dialog.setupBottomSheetDialog
import com.example.ecommerceapp.utils.Resource
import com.example.ecommerceapp.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        binding.tvDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.apply {
            buttonLoginLogin.setOnClickListener {
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()

                viewModel.login(email, password)
            }
        }

        binding.tvForgotPasswordLogin.setOnClickListener {
            setupBottomSheetDialog { email ->

                viewModel.resetPassword(email)


            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        Snackbar.make(
                            requireView(),
                            "Reset link was sent to your email",
                            Snackbar.LENGTH_LONG
                        ).show()

                    }

                    is Resource.Error -> {
                        Snackbar.make(
                            requireView(),
                            "Error ${it.message.toString()}",
                            Snackbar.LENGTH_LONG
                        ).show()

                    }

                    else -> {
                        Unit
                    }
                }
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
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
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