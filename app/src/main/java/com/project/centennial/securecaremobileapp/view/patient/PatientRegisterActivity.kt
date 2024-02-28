package com.project.centennial.securecaremobileapp.view.patient

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientRegisterBinding
import com.project.centennial.securecaremobileapp.utils.Gender
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.utils.UserType
import com.project.centennial.securecaremobileapp.utils.Utilities
import com.project.centennial.securecaremobileapp.view.MainActivity
import com.project.centennial.securecaremobileapp.view.shared.DatePickerFragment
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

class PatientRegisterActivity: DrawerBaseActivity(), DatePickerFragment.DateSelectionListener {
    private lateinit var binding: ActivityPatientRegisterBinding
    private val userViewModel: RegisterViewModel by viewModels()
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientRegisterBinding.inflate(layoutInflater)
        allocateActivityTitle("User Register")
        setContentView(binding.root);
        hideGroupHeaderAuthorization()
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        binding.dayofbirthButton.setOnClickListener{clickDobButton()}
        binding.insertAddButton.setOnClickListener{onSave()}

        setGenderSpinner()

        listenerHandler()
    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {

        var txtMonth = if(month > 8 ) "${month + 1}" else "0${month + 1}"
        var txtDay= if(day > 10 ) "$day" else "0${day}"
        var selectedDate = "$year-${txtMonth}-$txtDay"

        binding.dayofbirthTextview.text = selectedDate

        //Toast.makeText(this, "Hello $selectedDate",Toast.LENGTH_LONG).show()
    }

    private fun setGenderSpinner() {
        // insert patient design
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, Gender.values()
        )
        binding.genderSpinner.adapter = adapter
        // department spinner events
        /*binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Toast.makeText(view, "gender: ${Gender.values()[position]}", Toast.LENGTH_LONG).show()
                binding.errorTextView.text = Gender.values()[binding.genderSpinner.selectedItemId.toInt()].id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }*/
    }

    private fun clickDobButton(){
        val newFragment = DatePickerFragment()
        newFragment.setDateSelectionListener(this) // Set the listener
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun redirect(){
        startActivity(Intent(this, MainActivity::class.java))

    }
    private fun listenerHandler() {
        lifecycleScope.launch {
            userViewModel.userStateFlow.collect {
                if(it != null){
                    if(it.status){
                        sharedPreferencesHelper.saveUserInfo(it)
                        redirect()
                        // Log.d("Register: ", "Successfully")
                    }
                    else
                        findViewById<TextView>(R.id.login_error).text = it.message
                }

            }
        }
    }
    private fun onSave(){
        var email = binding.email.text.toString();
        var firstname = binding.firstname.text.toString();
        var lastname = binding.lastname.text.toString()
        var phone = binding.phone.text.toString()
        var password = binding.password.text.toString()
        var address = binding.address.text.toString()
        var gender = Gender.values()[binding.genderSpinner.selectedItemId.toInt()].id
        var dob = binding.dayofbirthTextview.text.toString()

        if(!Utilities.isValidEmail(email)){
            binding.errorTextView.text = "Invalid Email."
            return
        }

        if(firstname.isEmpty() || lastname.isEmpty()){
            binding.errorTextView.text = "First name & Last name cannot be empty."
            return
        }

        if(phone.isEmpty() || phone.length < 10){
            binding.errorTextView.text = "The password must be at least 10 digits long and cannot be empty."
            return
        }

        if(password.isEmpty() || password.length < 8 ){
            binding.errorTextView.text = "The password must be at least 8 characters long and cannot be empty."
            return
        }

        if(gender.isEmpty()){
            binding.errorTextView.text = "Gender cannot be empty."
            return
        }
        if(dob.isEmpty()){
            binding.errorTextView.text = "Day of birth cannot be empty."
            return
        }

        var usertypeid = UserType.Patient.id

        val body = mapOf(
            "firstname" to firstname,
            "lastname" to lastname,
            "email" to email,
            "password" to password,
            "usertypeid" to usertypeid,
            "phone" to phone,
            "gender" to gender,
            "dob" to dob,
            "address" to address
        )

        userViewModel.registerPatient(body)

    }
}