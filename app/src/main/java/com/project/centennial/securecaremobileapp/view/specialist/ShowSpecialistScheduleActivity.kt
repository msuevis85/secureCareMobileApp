package com.project.centennial.securecaremobileapp.view.specialist

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.databinding.ActivityShowSpecialistScheduleBinding
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.view.shared.DatePickerFragment
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.view.specialist.adapters.SpecialistScheduleAdapter
import com.project.centennial.securecaremobileapp.viewmodel.BookAppointmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
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
    private val viewModel: BookAppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowSpecialistScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Specialist Schedule Viewer")
        hideGroupHeaderAuthorization()
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with empty list
        val emptyDataArrayResponse = DataArrayResponse(status = false, message = "", data = emptyList())
        adapter = SpecialistScheduleAdapter(emptyDataArrayResponse)
        recyclerView.adapter = adapter

        // Initialize CalendarView
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
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

    private fun fetchSpecialistSchedules(date: Date) {
        GlobalScope.launch(Dispatchers.Main) {
            // Get token from SharedPreferences
            val token = sharedPreferencesHelper.getUserToken()?.toString()
            val userInfo = sharedPreferencesHelper.getUserInfo()

            if (token != null && userInfo != null && userInfo.userid != null) {
                val specialistID = userInfo.userid
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateString = dateFormat.format(date)
                Log.d("Token : ", token.toString())
                // Call ViewModel to fetch specialist schedules
                viewModel.getSpecialistSchedules("token", dateString, specialistID)


                // Observe the ViewModel's state flow
                viewModel.schedulesStateFlow.collect { response ->
                    if (response != null && response.status) {
                        // Update RecyclerView adapter with fetched data
                        adapter.updateList(response)
                    } else {
                        // Handle error
                        // Show error message or retry logic
                        Toast.makeText(
                            this@ShowSpecialistScheduleActivity,
                            "Error fetching schedules",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                // Show error message
                Toast.makeText(
                    this@ShowSpecialistScheduleActivity,
                    "User information or token is not available.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



}
