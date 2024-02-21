package com.project.centennial.securecaremobileapp.view.patient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.centennial.securecaremobileapp.databinding.ActivityBookSpecialistAppointmentBinding
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.view.shared.DatePickerFragment
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.view.user.LoginActivity
import com.project.centennial.securecaremobileapp.viewmodel.BookAppointmentViewModel
import kotlinx.coroutines.launch

class BookSpecialistAppointmentActivity: DrawerBaseActivity(), DatePickerFragment.DateSelectionListener {
    private lateinit var binding: ActivityBookSpecialistAppointmentBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private val bookApmtViewModel: BookAppointmentViewModel by viewModels()
    private lateinit var selectedSpecialistId: String
    private lateinit var token: String
    private lateinit var selectedTime: String
    private lateinit var selectedDate: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookSpecialistAppointmentBinding.inflate(layoutInflater)
        allocateActivityTitle("Book Specialist Appointment")
        setContentView(binding.root);
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        binding.dateButton.setOnClickListener{clickDateButton()}
        binding.bookAppointmentButton.setOnClickListener{clickBookAppointment()}
        listenerHandler()
        checkToken()
    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {

        var txtMonth = if(month > 8 ) "${month + 1}" else "0${month + 1}"
        var txtDay= if(day > 10 ) "$day" else "0${day}"
        selectedDate = "$year-${txtMonth}-$txtDay"
        binding.dateTextview.text = selectedDate

        bookApmtViewModel.getSpecialistSchedules(token,selectedDate,selectedSpecialistId)
    }

    private fun checkToken() {
        token = sharedPreferencesHelper.getUserToken().toString();
        if(token == ""){
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            bookApmtViewModel.getSpecialists(token!!);
        }
    }

    private fun setSpecialistSpinner(specialists: List<Map<String, Any>>) {
        val specialistList: List<Pair<String, String>> = specialists.map {
            val id: String = it["specialistid"] as String?:""
            val name = "${it["firstname"]} ${it["lastname"]}" as? String ?: ""
            Pair(name, id)
        }


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            specialistList.map { it.first }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSpecialists.adapter = adapter

        binding.spinnerSpecialists.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedSpecialistId = specialistList[position].second
                Log.d("specialist id: ", "$selectedSpecialistId")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where nothing is selected
            }
        }
    }
    private fun setTimeRadioGroup(hours: List<Map<String, Any>>){
        val hoursList: List<String> = hours.map { it["starttime"] as? String ?: "" }
        Toast.makeText(this, "setTimeRadioGroup: ${hoursList.size}", Toast.LENGTH_SHORT).show()

        var layout: LinearLayout? = null
        var checkedRadioButtonId = -1
        hoursList.forEachIndexed { index, optionText ->
            val radioButton = RadioButton(this)
            radioButton.text = optionText
            radioButton.id = index // Set a unique id for each RadioButton

            if(index % 2 == 0){
                layout = LinearLayout(this)
                binding.radiogroupContainer.addView(layout)
                layout!!.addView(radioButton)

                val layoutParams = radioButton.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.leftMargin = 5 // Set the start margin in pixels
                radioButton.layoutParams = layoutParams

            } else {
                layout!!.addView(radioButton)
                layout = null;
                val layoutParams = radioButton.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.rightMargin = 5 // Set the start margin in pixels
                radioButton.layoutParams = layoutParams
            }

            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    if(checkedRadioButtonId != -1) {
                        var indexLayout = checkedRadioButtonId/2
                        var indexRadioBtn = checkedRadioButtonId %2
                        var prevLayout = binding.radiogroupContainer.getChildAt(indexLayout) as LinearLayout
                        var radidoBtn = prevLayout.getChildAt(indexRadioBtn) as RadioButton
                        radidoBtn.isChecked = false
                    }
                    checkedRadioButtonId = buttonView.id
                    selectedTime = hoursList[checkedRadioButtonId]
                    //Toast.makeText(this, "selectedTime: $selectedTime", Toast.LENGTH_SHORT).show()

                }
            }
        }


    }

    private fun clickDateButton(){
        val newFragment = DatePickerFragment()
        newFragment.setDateSelectionListener(this) // Set the listener
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun clickBookAppointment() {
        var userId = sharedPreferencesHelper.getUserInfo()!!.userid

        val obj = mapOf(
            "specialistid" to selectedSpecialistId,
            "userid" to userId,
            "meetingtime" to selectedTime,
            "meetingdate" to selectedDate

        )

        bookApmtViewModel.bookAppointment(token, body = obj)
    }

    private fun showBookedAppointment(response: DataResponse) {

        if(response.status && response.data != null){
            val meetingtime: String? = response.data["meetingtime"] as? String
            val meetingdate: String? = response.data["meetingdate"] as? String
            val status: String? = response.data["status"] as? String
            var result = "${response.message}\nMeeting Date: ${meetingdate} - Meeting Time: ${meetingtime}" +
                    "\nStatus: ${status}"
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()

        }

    }
    private fun listenerHandler() {
        lifecycleScope.launch {
            bookApmtViewModel.specialistsStateFlow.collect {
                if(it != null){
                    //Log.d("Book Appointment: ", "Successfully ${it.status}")
                    //Log.d("Book Appointment data: ", "Successfully ${it.specialists}")
                    val specialists: List<Map<String, Any>> = it.data

                    // Accessing values from the specialists map
                    /* val specialistName: String? = specialists[0]["firstname"] as? String
                    val specialistId: String? = specialists[0]["lastname"] as? String
                    Log.d("Book Appointment: ", "specialistName ${specialistName}")
                    */
                    setSpecialistSpinner(specialists)

                }


            }
        }

        // available schedule
        lifecycleScope.launch {
            bookApmtViewModel.schedulesStateFlow.collect {
                if(it != null){
                    val hours: List<Map<String, Any>> = it.data
                    setTimeRadioGroup(hours)

                }
            }
        }

        // book appointment
        lifecycleScope.launch {
            bookApmtViewModel.appointmentStateFlow.collect {
                if(it != null ){
                    showBookedAppointment(it)
                }
            }
        }
    }


}