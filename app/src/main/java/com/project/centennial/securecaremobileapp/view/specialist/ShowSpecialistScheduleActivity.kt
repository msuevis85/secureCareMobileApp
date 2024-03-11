package com.project.centennial.securecaremobileapp.view.specialist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.ActivityShowSpecialistScheduleBinding
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.adapters.SpecialistScheduleAdapter
import com.project.centennial.securecaremobileapp.view.user.LoginActivity
import com.project.centennial.securecaremobileapp.viewmodel.ShowSpecialistScheduleViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ShowSpecialistScheduleActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityShowSpecialistScheduleBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpecialistScheduleAdapter
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private val specialistViewModel: ShowSpecialistScheduleViewModel by viewModels()
    private var token: String = ""
    private var userid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowSpecialistScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Specialist Schedule Viewer")
        hideGroupHeaderAuthorization()
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        checkToken()
        listenerHandler()
        setupRecyclerView()
        setupCalendarView()


    }

    private fun setupCalendarView() {
        // Initialize CalendarView
        val calendarView = binding.calendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.time
            fetchSpecialistSchedules(selectedDate)
        }

        // Fetch specialist schedules for the current date
        val currentDate = Calendar.getInstance().time
        fetchSpecialistSchedules(currentDate)
    }
    private fun setupRecyclerView(){
        // Initialize RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with empty list

        val emptyDataArrayResponse: List<Map<String, Any>> = emptyList()
        adapter = SpecialistScheduleAdapter(emptyDataArrayResponse)
        recyclerView.adapter = adapter
    }
    private fun checkToken() {
        token = sharedPreferencesHelper.getUserToken().toString();
        var user = sharedPreferencesHelper.getUserInfo()

        if(token == "" || user["userid"].toString().isEmpty()){

            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        userid = user["userid"].toString()

        //specialistViewModel.getSchedulesByDate(token,userid,"2024-03-10")

    }

    private fun listenerHandler(){
        lifecycleScope.launch {
            specialistViewModel.schedulesStateFlow.collect {
                if (it != null) {
                    if (it.status) {
                        adapter.updateList(it.data)
                        if(it.data.isEmpty())
                            showToast("No Appointment")
                    } else {
                        showToast("Error fetching schedules")
                    }

                }

            }
        }
    }
    private fun showToast(message: String){
        Toast.makeText(
            this@ShowSpecialistScheduleActivity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun fetchSpecialistSchedules(date: Date) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = dateFormat.format(date)
        //Log.d("Specialist Schedule: token: ",token )
        //Log.d("Specialist Schedule: userid: ",userid )
        //Log.d("Specialist Schedule: dateString: ",dateString )

        specialistViewModel.getSchedulesByDate(token,userid,dateString)

    }



}