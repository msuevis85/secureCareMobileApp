package com.project.centennial.securecaremobileapp.view


import android.os.Bundle
import com.project.centennial.securecaremobileapp.databinding.ActivityMainBinding
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity



class MainActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var token = SharedPreferencesHelper(this).getUserToken()
        //Toast.makeText(this, " Hello $token", Toast.LENGTH_LONG).show()
    }

}
