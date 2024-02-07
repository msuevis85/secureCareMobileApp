package com.project.centennial.securecaremobileapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.centennial.securecaremobileapp.SecureCareMobileApplication
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.viewmodel.NurseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var nurseViewModel: NurseViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val logginButton = findViewById<Button>(R.id.InitSessionButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        logginButton.setOnClickListener {
            login()
        }


        cancelButton.setOnClickListener{
            goBack()
        }
    }


    fun login(){
        if(!isNumeric(findViewById<TextView>(R.id.username).text.toString())){
            Toast.makeText(this.applicationContext,"User or password is invalid", Toast.LENGTH_LONG).show()
        } else {
            val nurseModelFactory = NurseViewModel.NurseViewModelFactory((application as SecureCareMobileApplication).nurseRepository)
            nurseViewModel = ViewModelProvider(this, nurseModelFactory).get(NurseViewModel::class.java)

            coroutineScope.launch {

                val logginTxt = findViewById<TextView>(R.id.username).text.toString()
                val passwordTxt = findViewById<TextView>(R.id.password).text.toString()



                var nurse = nurseViewModel.login(logginTxt.toInt(),passwordTxt)
                if(nurse!=null){
                    addSession(nurse.nurseId, nurse.firstname, nurse.lastname)
                    goBack()
                }
            }
        }
    }

    fun goBack(){
        intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
    }

    private fun addSession(nurseId: Int, firstname: String, lastName: String){
        val myPreference = getSharedPreferences("session", MODE_PRIVATE)
        val preferenceEditor = myPreference.edit()
        preferenceEditor.putString("nurseId", nurseId.toString())
        preferenceEditor.putString("firstname", firstname)
        preferenceEditor.putString("lastName", lastName)
        preferenceEditor.commit()
    }

    fun isNumeric(toCheck: String): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return toCheck.matches(regex)
    }
}