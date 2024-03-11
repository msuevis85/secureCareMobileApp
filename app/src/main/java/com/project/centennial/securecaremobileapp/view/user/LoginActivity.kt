package com.project.centennial.securecaremobileapp.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.centennial.securecaremobileapp.databinding.ActivityLoginBinding
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.utils.Utilities
import com.project.centennial.securecaremobileapp.view.MainActivity
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : DrawerBaseActivity() {

    private val userViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        allocateActivityTitle("User Login")
        setContentView(binding.root)
        hideGroupHeaderAuthorization()

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        binding.loginButton.setOnClickListener {
            login()
        }
        binding.cancelButton.setOnClickListener{
            cancel()
        }
        listenerHandler()
    }

    private fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        if(!Utilities.isValidEmail(email)){
            //Toast.makeText(this.applicationContext,"User or password is invalid", Toast.LENGTH_LONG).show()
            binding.loginError.text = "Invalid Email."
        } else if(password.length < 2){
            binding.loginError.text = "Invalid Password."
        }else {
            // userViewModel.loginWithApi("nihealth@gmail.com","123456787")
            val body = mapOf(
                "email" to email,
                "password" to password
            )
            userViewModel.loginWithApi(body)
        }
    }

    private fun cancel(){
        // set empty for all textfields
        // or next action following your workflow
    }

    private fun loginSuccessfully(){
        //var token = sharedPreferencesHelper.getUserToken()
        //Toast.makeText(this, "Token: $token", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun listenerHandler() {
        lifecycleScope.launch {
            userViewModel.userStateFlow.collect {
                if(it != null){
                    if(it.status){
                        sharedPreferencesHelper.saveUserInfo(it)
                        loginSuccessfully()
                        Log.d("Login: ", "successfully ${it.data["firstname"]}")
                        Log.d("Login: ", "successfully ${it.data["usertypeid"]}")
                    }
                    else
                        binding.loginError.text = it.message
                }

            }
        }
    }


}