package com.project.centennial.securecaremobileapp.view.patient

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientProfileBinding
import com.project.centennial.securecaremobileapp.utils.Gender
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.view.shared.DatePickerFragment
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.viewmodel.UserProfileViewModel
import kotlinx.coroutines.launch

class PatientProfileActivity: DrawerBaseActivity(), DatePickerFragment.DateSelectionListener{
    private lateinit var binding: ActivityPatientProfileBinding
    private val userViewModel: UserProfileViewModel by viewModels()
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var genderId: String = "";
    private lateinit var selectedDate: String;
    private lateinit var user: Map<String, Any>;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root);
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        setGenderSpinner()
        listenerHandler()

        var token = sharedPreferencesHelper.getUserToken();
        var user = sharedPreferencesHelper.getUserInfo()

        userViewModel.getProfile(token!!, user!!["userid"].toString(), (user!!["usertypeid"] as Double).toInt())

        binding.saveButton.setOnClickListener{onSave(token)}
        binding.dayofbirthButton.setOnClickListener{clickDobButton()}

    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {

        var txtMonth = if(month > 8 ) "${month + 1}" else "0${month + 1}"
        var txtDay= if(day > 10 ) "$day" else "0${day}"
        selectedDate = "$year-${txtMonth}-$txtDay"
        binding.dayofbirthTextview.text = selectedDate
    }

    private fun setUserInfoText(_user: Map<String, Any>){
        user = _user;
        binding.firstname.setText(user["firstname"].toString())
        binding.lastname.setText(user["lastname"].toString())
        binding.dayofbirthTextview.text = user["dob"].toString()
        binding.phone.setText(user["phone"].toString())
        binding.address.setText(user["address"].toString())
        genderId = user["gender"].toString()

        Toast.makeText(this, "usertypeId: ${user["usertypeid"]}", Toast.LENGTH_LONG).show()

    }

    private fun clickDobButton(){
        val newFragment = DatePickerFragment()
        newFragment.setDateSelectionListener(this) // Set the listener
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun setGenderSpinner() {
        // insert patient design
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, Gender.values()
        )
        binding.genderSpinner.adapter = adapter
        val selectedGender = Gender.values().find { it.id == genderId }
        val selectedIndex = Gender.values().indexOf(selectedGender)
        binding.genderSpinner.setSelection(selectedIndex)
    }

    private fun showResultMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun listenerHandler() {
        lifecycleScope.launch {
            userViewModel.userStateFlow.collect {
                if(it != null){
                    if(it.status){
                        sharedPreferencesHelper.saveUserInfo(it)
                        setUserInfoText(it.data)
                        Log.d("Register: ", "Successfully")
                    }
                    else
                        binding.errorTextView.text = it.message
                }

            }
        }
        // update profile
        lifecycleScope.launch {
            userViewModel.updateProfileStateFlow.collect {
                if(it != null){
                    if(it.status){
                        sharedPreferencesHelper.saveUserInfo(it)
                        showResultMessage(it.message)
                    }
                    else
                        binding.errorTextView.text = it.message
                }

            }
        }
    }

    private fun onSave(token: String){
        var firstname = binding.firstname.text.toString();
        var lastname = binding.lastname.text.toString()
        var phone = binding.phone.text.toString()
        var address = binding.address.text.toString()
        var gender = Gender.values()[binding.genderSpinner.selectedItemId.toInt()].id
        var dob = binding.dayofbirthTextview.text.toString()


        if(firstname.isEmpty() || lastname.isEmpty()){
            binding.errorTextView.text = "First name & Last name cannot be empty."
            return
        }

        if(phone.isEmpty() || phone.length < 10){
            binding.errorTextView.text = "The password must be at least 10 digits long and cannot be empty."
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

        var data =  mapOf(
            "userid" to user["userid"],
            "firstname" to firstname,
            "lastname" to lastname,
            "usertypeid" to user["usertypeid"],
            "phone" to phone,
            "address" to address,
            "gender" to gender,
            "dob" to dob
        )

        Toast.makeText(this, "Updated Info: ${data}", Toast.LENGTH_LONG ).show()

        userViewModel.updatePatient(token, data)

    }
}