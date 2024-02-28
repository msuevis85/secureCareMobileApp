package com.project.centennial.securecaremobileapp.view.patient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.centennial.securecaremobileapp.databinding.ActivityRequestAppointmentBinding
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.view.user.LoginActivity
import com.project.centennial.securecaremobileapp.viewmodel.RequestAppointmentViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RequestAppointmentActivity: DrawerBaseActivity() {
    private lateinit var binding: ActivityRequestAppointmentBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private val requestApmtViewModel: RequestAppointmentViewModel by viewModels()
    private  var selectedSpecialistId: String = ""
    private var medicalspecialtyid: Int = -1;
    private var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestAppointmentBinding.inflate(layoutInflater)
        allocateActivityTitle("Book Specialist Appointment")
        setContentView(binding.root);
        selectedSpecialistId = intent.getStringExtra("SPECIALIST_ID").toString()
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        binding.bookAppointmentButton.setOnClickListener{clickBookAppointment()}
        listenerHandler()
        checkToken()
    }


    private fun checkToken() {
        token = sharedPreferencesHelper.getUserToken().toString();
        if(token == ""){
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        requestApmtViewModel.getMedicalSpecialties();
    }


    private fun setMedicalSpecialtySpinner(data: List<Map<String, Any>>) {
        val adapter = ArrayAdapter (
            this,
            android.R.layout.simple_spinner_item,
            data.map { it["name"].toString() } // Extracting the "name" value from each map
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMedicalSpecialty.adapter = adapter

        binding.spinnerMedicalSpecialty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                medicalspecialtyid = data[position]["medicalspecialtyid"].toString().toInt()


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    private fun clickBookAppointment() {


        var userId = sharedPreferencesHelper.getUserInfo()["userid"]

        val obj = mapOf(
            "userid" to userId,
            "medicalspecialtyid" to medicalspecialtyid,
            "symptom" to ""
        )

        requestApmtViewModel.requestAppointment(token, obj)
    }

    private fun showBookedAppointment(response: DataResponse) {

        if(response.status ){
            //val meetingtime: String? = response.data["meetingtime"] as? String

            var result = "${response.message}\n ${response.data.toString()}"
            Toast.makeText(this,result, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()

        }

    }

    private fun listenerHandler() {

        // get medical specialties
        lifecycleScope.launch {
            requestApmtViewModel.medicalSpecialtiesStateFlow
                .map { it }
                .distinctUntilChanged().collect {dataState ->
                if(dataState != null){
                    setMedicalSpecialtySpinner(dataState.data)
                }
            }
        }

        // book appointment
        lifecycleScope.launch {
            requestApmtViewModel.appointmentStateFlow.collect {
                if(it != null ){
                    showBookedAppointment(it)
                }
            }

        }

    }

}