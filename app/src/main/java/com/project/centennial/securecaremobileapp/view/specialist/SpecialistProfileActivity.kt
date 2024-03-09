package com.project.centennial.securecaremobileapp.view.specialist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.centennial.securecaremobileapp.databinding.ActivitySpecialistProfileBinding
import com.project.centennial.securecaremobileapp.utils.Gender
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.view.MainActivity
import com.project.centennial.securecaremobileapp.view.shared.DatePickerFragment
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.viewmodel.UserProfileViewModel
import kotlinx.coroutines.launch
import java.util.Arrays




class SpecialistProfileActivity: DrawerBaseActivity(), DatePickerFragment.DateSelectionListener{
    private lateinit var binding: ActivitySpecialistProfileBinding
    private val userViewModel: UserProfileViewModel by viewModels()
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var genderId: String = "";
    private var medicalspecialtyid: Int = -1;
    private var token: String = "";
    private lateinit var selectedDate: String;
    private lateinit var user: Map<String, Any>;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecialistProfileBinding.inflate(layoutInflater)
        setContentView(binding.root);
        sharedPreferencesHelper = SharedPreferencesHelper(this)


        listenerHandler()
        checkToken()


    }

    private fun checkToken(){
        token = sharedPreferencesHelper.getUserToken().toString();
        var user = sharedPreferencesHelper.getUserInfo()

        if(token == ""){
            startActivity(Intent(this, MainActivity::class.java))
            return
        }

        userViewModel.getProfile(token, user!!["userid"].toString(), (user!!["usertypeid"] as Double).toInt())
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
        Toast.makeText(this, "user: ${_user}", Toast.LENGTH_LONG).show()
        user = _user;
        binding.firstname.setText(user["firstname"].toString())
        binding.lastname.setText(user["lastname"].toString())
        binding.dayofbirthTextview.text = user["dob"].toString()
        binding.phone.setText(user["phone"].toString())
        binding.address.setText(user["address"].toString())
        genderId = user["gender"].toString()
        medicalspecialtyid = user["medicalspecialtyid"].toString().toInt()

        Toast.makeText(this, "usertypeId: ${user["usertypeid"]}", Toast.LENGTH_LONG).show()

        // need to get gender from db first, then setting default selected item for spinner
        setGenderSpinner()

        // need to get medicalspecialtyid first, so when get list of medical specialty from db
        // being able to set default selected item for spinner
        userViewModel.getMedicalSpecialties()

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

        Toast.makeText(this, "genderId: $selectedGender", Toast.LENGTH_LONG ).show()
        binding.genderSpinner.setSelection(selectedIndex)
    }

    private fun setMedicalSpecialtySpinner(data: List<Map<String, Any>>) {
        // insert patient design
       //Toast.makeText(this, "setMedicalSpecialtySpinner size: ${data.size}", Toast.LENGTH_LONG).show();
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            data.map { it["name"].toString() } // Extracting the "name" value from each map
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.specialiationSpinner.adapter = adapter

        val defaultIndex = data.indexOfFirst { it["medicalspecialtyid"] == medicalspecialtyid.toString() }
        Toast.makeText(this, "medicalspecialtyid: ${medicalspecialtyid} : ${defaultIndex}", Toast.LENGTH_LONG).show()
        binding.specialiationSpinner.setSelection(if (defaultIndex != -1) defaultIndex else 0)

        binding.specialiationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                medicalspecialtyid = data[position]["medicalspecialtyid"].toString().toInt()
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun showResultMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun listenerHandler() {
        // get profile
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

        // get medical specialties
        lifecycleScope.launch {
            userViewModel.medicalSpecialtiesStateFlow.collect {
                if(it != null){
                    if(it.status){
                        setMedicalSpecialtySpinner(it.data)
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

        val body = mapOf(
            "userid" to user["userid"],
            "firstname" to firstname,
            "lastname" to lastname,
            "usertypeid" to user["usertypeid"],
            "phone" to phone,
            "gender" to gender,
            "dob" to dob,
            "address" to address,
            "medicalspecialtyid" to medicalspecialtyid
        )

        Toast.makeText(this, "Updated Info: ${body}", Toast.LENGTH_LONG ).show()

        userViewModel.updateSpecialist(token, body)

    }
}