package com.project.centennial.securecaremobileapp.view.specialist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.ActivityCheckingWaitingAppointmentsBinding
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.utils.Utilities
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.view.user.LoginActivity
import com.project.centennial.securecaremobileapp.viewmodel.CheckingWaitingAppointmentsViewModel
import kotlinx.coroutines.launch
class CheckingWaitingAppointmentsActivity : DrawerBaseActivity(), CheckingWaitingAppointmentAdapter.WaitingAppointmentId {

    private lateinit var binding: ActivityCheckingWaitingAppointmentsBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private val checkingWaitingViewModel: CheckingWaitingAppointmentsViewModel by viewModels()
    private var token: String = ""
    private var user: Map<String, Any> = emptyMap()
    private var appointments: List<Map<String, Any>>? =null;

    private val adapter: CheckingWaitingAppointmentAdapter by lazy {
        CheckingWaitingAppointmentAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckingWaitingAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        listenerHandler()
        setupRecyclerView()
        checkToken()

    }

    private fun checkToken() {
        token = sharedPreferencesHelper.getUserToken().toString();
        user = sharedPreferencesHelper.getUserInfo()

        if(token == "" || user["userid"].toString().isEmpty()){
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        //Log.d("Check Waiting Room - token: ",token )
        checkingWaitingViewModel.getWaitingAppointments(token,user["userid"].toString());
    }
    private fun setupRecyclerView(){
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }


    private fun listenerHandler() {
        // get appointments
        lifecycleScope.launch {
            checkingWaitingViewModel.appointmentsFlow.collect {
                if (it != null) {
                    if (it.status) {
                        appointments = it.data
                        adapter.submitList(appointments!!.toMutableList())
                        binding.recyclerView.invalidateItemDecorations()

                        if (it.data.isEmpty())
                            binding.noAppointmentTextview.visibility = View.VISIBLE
                        else binding.noAppointmentTextview.visibility = View.GONE

                    } else {
                    }

                }

            }
        }

        // confirm appointments
        lifecycleScope.launch {
            checkingWaitingViewModel.confirmedAppointmentFlow
                .collect {dataState ->
                    if (dataState  != null) {
                        if (dataState .status) {
                            showToast("onConfirmAppointment: ${dataState .message}")
                            // remove appointment out of the list
                        } else {
                            showToast("onConfirmAppointment: ${dataState .message}")

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

    override fun onConfirmAppointment(body: Map<String, Any>) {

        val newKey = "userid"
        val newValue = user["userid"].toString()
        val updatedMap = body + (newKey to newValue)
        Log.d("onConfirmAppointment: ",updatedMap.toString())
        checkingWaitingViewModel.confirmAppointment(token, updatedMap)
        //checkingWaitingViewModel.confirmAppointment(token, body)
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}