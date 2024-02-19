package com.project.centennial.securecaremobileapp.view.user

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.databinding.ActivityUserProfileBinding
import com.project.centennial.securecaremobileapp.model.User
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.viewmodel.UserProfileViewModel
import kotlinx.coroutines.launch

class UserProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private val userViewModel: UserProfileViewModel by viewModels()
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root);
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        listenerHandler()

        var token = sharedPreferencesHelper.getUserToken();
        var user = sharedPreferencesHelper.getUserInfo()

        if(token!!.isNotEmpty()) {
            userViewModel.getProfile(token, user!!.userid, user!!.usertypeid)
        }

        // Toast.makeText(this, "user: ${user!!.usertypeid}", Toast.LENGTH_LONG).show()
        // Toast.makeText(this, "Token: ${token}", Toast.LENGTH_LONG).show()

    }

    private fun setUserInfoText(user: User){
        binding.firstname.setText(user.firstname)
        binding.lastname.setText(user.lastname)
        binding.dayofbirthTextview.text = user.dob
        binding.email.setText(user.email)
        binding.phone.setText(user.phone)
        binding.address.setText(user.address)
        binding.gender.setText(user.gender)

    }

    private fun listenerHandler() {
        lifecycleScope.launch {
            userViewModel.userStateFlow.collect {
                if(it != null){
                    if(it.status){
                        sharedPreferencesHelper.saveUserInfo(it)
                        setUserInfoText(it.user)
                        Log.d("Register: ", "Successfully")
                    }
                    else
                        findViewById<TextView>(R.id.login_error).text = it.message
                }

            }
        }
    }
}