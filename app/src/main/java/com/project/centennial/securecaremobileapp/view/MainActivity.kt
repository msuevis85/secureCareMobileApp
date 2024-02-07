package com.project.centennial.securecaremobileapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.view.Patient.PatientList
import com.project.centennial.securecaremobileapp.view.Test.TestAdd

private const val LOGOUT = "Logout"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pattientButton = findViewById<Button>(R.id.patientButton)
        val pattientImageButton = findViewById<ImageButton>(R.id.pasientImageButton)
        val logginButton = findViewById<Button>(R.id.loginButton)
        val loginImageButton = findViewById<ImageButton>(R.id.loginImageButton)
        val testButton = findViewById<Button>(R.id.testButton)
        val testImageButton = findViewById<ImageButton>(R.id.testImageButton)


        val myPreference = getSharedPreferences("session", MODE_PRIVATE)
        val nurseId = myPreference.getString("nurseId",null)
        val firstname = myPreference.getString("firstname", "")
        val lastName = myPreference.getString("lastName", "")

        if(nurseId!=null){
            val title = findViewById<TextView>(R.id.title);
            title.visibility = View.VISIBLE
            title.text = " Hello, "+firstname + " " + lastName

            pattientButton.isEnabled = true
            testButton.isEnabled = true
            pattientImageButton.isEnabled = true
            testImageButton.isEnabled = true

            loginImageButton.setImageResource(R.drawable.logout)
            logginButton.setText(LOGOUT)
        }


        logginButton.setOnClickListener {
            if (!LOGOUT.equals(logginButton.text.toString())) {
                goToLogginActivity()
            } else {
                destroySharedPreferences()
                refresh()
            }

        }

        loginImageButton.setOnClickListener {
            if (!LOGOUT.equals(logginButton.text.toString())) {
                goToLogginActivity()
            } else {
                destroySharedPreferences()
                refresh()
            }
        }

        pattientButton.setOnClickListener {
            val intent = Intent(this,PatientList::class.java)
            startActivity(intent)
        }

        pattientImageButton.setOnClickListener {
            val intent = Intent(this,PatientList::class.java)
            startActivity(intent)
        }

        testButton.setOnClickListener {
            val intent = Intent(this,TestAdd::class.java)
            startActivity(intent)
        }

        testImageButton.setOnClickListener {
            val intent = Intent(this,TestAdd::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        destroySharedPreferences()
        super.onDestroy()
    }



    fun goToLogginActivity(){
        intent = Intent(applicationContext,LoginActivity::class.java)
        startActivity(intent)
    }


    private fun destroySharedPreferences() {
        val myPreference = getSharedPreferences("session", MODE_PRIVATE)
        val editor = myPreference.edit()

        // Clearing all data from Shared Preferences
        editor.clear()
        editor.remove("nurseId")

        // Commit the changes
        editor.commit();
    }

    fun refresh(){
        intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        }
}
