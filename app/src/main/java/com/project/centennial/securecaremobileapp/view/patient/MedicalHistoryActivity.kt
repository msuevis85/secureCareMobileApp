package com.project.centennial.securecaremobileapp.view.patient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.ActivityMainBinding
import com.project.centennial.securecaremobileapp.databinding.ActivityMedicalHistoryBinding
import com.project.centennial.securecaremobileapp.utils.AppointmentStatus
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.view.specialist.SearchSpecialistsAdapter
import com.project.centennial.securecaremobileapp.view.user.LoginActivity
import com.project.centennial.securecaremobileapp.viewmodel.MedicalHistoryViewModel
import com.project.centennial.securecaremobileapp.viewmodel.SearchSpecialistViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MedicalHistoryActivity : DrawerBaseActivity(), MedicalHistoryAdapter.AppointmentHistoryId {

    private lateinit var binding: ActivityMedicalHistoryBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private val medicalViewModel: MedicalHistoryViewModel by viewModels()
    private var token: String = ""
    private var appointments: List<Map<String, Any>>? =null;

    private val adapter: MedicalHistoryAdapter by lazy {
        MedicalHistoryAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        checkToken()
        setupRecyclerView()
        listenerHandler()
    }

    private fun checkToken() {
        token = sharedPreferencesHelper.getUserToken().toString();
        var user = sharedPreferencesHelper.getUserInfo()

        if(token == "" || user["userid"].toString().isEmpty()){
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        medicalViewModel.getAppointments(token,user["userid"].toString());
    }
    private fun setupRecyclerView(){
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }



    private fun listenerHandler() {
        // get appointments
        lifecycleScope.launch {
            medicalViewModel.appointmentsStateFlow.collect {
                if (it != null) {
                    if (it.status) {
                        appointments = it.data
                        adapter.submitList(appointments!!.toMutableList())

                        binding.recyclerView.invalidateItemDecorations()
                    } else {
                    }

                }

            }
        }

        // cancal appointments
        lifecycleScope.launch {
            medicalViewModel.dataStateFlow
                .collect {dataState ->
                    if (dataState  != null) {
                        if (dataState .status) {
                            showToast("onCancelAppointment: ${dataState .message}")
                            updateAppointmentStatus(dataState .data["appointmentid"].toString(), dataState .data["status"].toString())
                        } else {
                            showToast("onCancelAppointment: ${dataState .message}")

                        }

                    }

                }
        }

        // update symptom
        lifecycleScope.launch {
            medicalViewModel.updateSymptomFlow.collect {dataState ->
                    if (dataState  != null) {
                        if (dataState .status) {
                            showToast("Edit/Update Symptom Appointment: ${dataState .message}")
                        } else {
                            showToast("Edit/Update Symptom Appointment: ${dataState .message}")

                        }

                    }

                }
        }

        // update appointment
        lifecycleScope.launch {
            medicalViewModel.updateAppointmentFlow.collect {dataState ->
                if (dataState  != null) {
                    if (dataState .status) {
                        showToast("Edit/Update Appointment: ${dataState .message}")
                    } else {
                        showToast("Edit/Update Appointment: ${dataState .message}")

                    }

                }

            }
        }
    }

    private fun updateAppointmentStatus(key: String, status: String) {
        if(appointments != null){
            val index = appointments!!.indexOfFirst { it["appointmentid"] == key }
            if (index != -1) {
                val updatedAppointment = appointments!![index].toMutableMap().apply {
                    set("status", status)
                }
                appointments = appointments!!.toMutableList().apply {
                    set(index, updatedAppointment)
                }
                adapter.submitList(appointments)
                binding.recyclerView.invalidateItemDecorations()
            } else {
                println("Appointment with ID $key not found")
            }
        }
    }

    override fun onCancelAppointment(appointmentid: String) {
        Log.d("onCancelAppointment: ",appointmentid)
        medicalViewModel.cancelAppointment(token, appointmentid)
    }

    override fun onEditAppointment(appointmentid: String, status: String) {
        if(status == AppointmentStatus.Waiting.id){
            // update symptom
            // I use hard-code, use your logic to update symptom request
            val obj = mapOf(
                "appointmentid" to appointmentid,
                "symptom" to "update symptom",
                "status" to status
            )
            medicalViewModel.updateSymptom(token,obj)

        } else if(status == AppointmentStatus.Confirmed.id) {
            // update appointment
            // I use hard-code, use your logic to call update appointment request
            val obj = mapOf(
                "appointmentid" to appointmentid,
                "symptom" to "update symptom",
                "status" to status,
                "meetingdate" to "2024-03-15",
                "meetingtime" to "10:30:00"
            )
            medicalViewModel.updateAppointment(token,obj)
        }
    }

    override fun onViewAppointment(appointmentid: String) {
        TODO("Not yet implemented")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}