package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.utils.Constants.USER_COLLECTION
import com.example.ecommerceapp.utils.RegisterValidation
import com.example.ecommerceapp.utils.Resource
import com.example.ecommerceapp.utils.validateEmail
import com.example.ecommerceapp.utils.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) :
    ViewModel() {


    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register: Flow<Resource<User>> = _register

    private val _validation = Channel<RegisterValidation.RegisterFieldState>()
    val validation = _validation.receiveAsFlow()
    fun createAccountWithEmailAndPassword(user: User) {

        if (checkValidation(user)) {
            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnSuccessListener { it ->

                    it.user?.let {
//                        _register.value = Resource.Success(it)

                        saveUserInfo(it.email!!, user)
                    }

                }.addOnFailureListener {
                    _register.value = Resource.Error(it.message.toString())
                }
        } else {
            val registerFieldState = RegisterValidation.RegisterFieldState(
                validateEmail(user.email), validatePassword(user.password)
            )
            runBlocking {
                _validation.send(registerFieldState)
            }
        }
    }

    private fun saveUserInfo(email: String, user: User) {
        db.collection(USER_COLLECTION).document(email).set(user).addOnSuccessListener {
            _register.value = Resource.Success(user)
        }.addOnFailureListener {
            _register.value = Resource.Error(it.message.toString())
        }
    }


    private fun checkValidation(user: User): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidate = validatePassword(user.password)
        return emailValidation is RegisterValidation.Success && passwordValidate is RegisterValidation.Success
    }

}