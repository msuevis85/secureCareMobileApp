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
import java.util.Calendar

class BookSpecialistAppointmentActivity: DrawerBaseActivity(), DatePickerFragment.DateSelectionListener {
    private lateinit var binding: ActivityBookSpecialistAppointmentBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private val bookApmtViewModel: BookAppointmentViewModel by viewModels()
    private  var selectedSpecialistId: String = ""
    private var medicalspecialtyid: Int = -1;
    private var token: String = ""
    private var selectedTime: String = ""
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookSpecialistAppointmentBinding.inflate(layoutInflater)
        allocateActivityTitle("Book Specialist Appointment")
        setContentView(binding.root);
        selectedSpecialistId = intent.getStringExtra("SPECIALIST_ID").toString()
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        listenerHandler()
        checkToken()

        binding.dateButton.setOnClickListener{clickDateButton()}
        binding.bookAppointmentButton.setOnClickListener{clickBookAppointment()}

    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {

        var txtMonth = if(month > 8 ) "${month + 1}" else "0${month + 1}"
        var txtDay= if(day > 9 ) "$day" else "0${day}"
        selectedDate = "$year-${txtMonth}-$txtDay"
        binding.dateTextview.text = selectedDate

        bookApmtViewModel.getSpecialistSchedules(token,selectedDate,selectedSpecialistId)
    }

    private fun checkToken() {
        token = sharedPreferencesHelper.getUserToken().toString();
        if(token == ""){
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        bookApmtViewModel.getMedicalSpecialties();
    }

    private fun setSpecialistSpinner(specialists: List<Map<String, Any>>) {
        val specialistList: List<Pair<String, String>> = specialists.map {
            val id: String = it["specialistid"] as String
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

        val defaultPosition = specialistList.indexOfFirst { it.second == selectedSpecialistId }

        if (defaultPosition != -1) {
            binding.spinnerSpecialists.setSelection(defaultPosition)
        }

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

                bookApmtViewModel.getSpecialists(token, medicalspecialtyid)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    private fun setTimeRadioGroup(hours: List<Map<String, Any>>){
        val hoursList: List<String> = hours.map { it["starttime"] as? String ?: "" }
        Toast.makeText(this, "setTimeRadioGroup: ${hoursList.size}", Toast.LENGTH_SHORT).show()

        var checkedRadioButtonId = -1
        selectedTime = ""

        binding.radiosColumn1.removeAllViews()
        binding.radiosColumn2.removeAllViews()
        binding.radiosColumn3.removeAllViews()
        binding.radiosColumn4.removeAllViews()

        hoursList.forEachIndexed { index, optionText ->
            val radioButton = RadioButton(this)
            radioButton.text = optionText
            radioButton.textSize = 14f
            radioButton.id = index // Set a unique id for each RadioButton

            if(index % 4 == 0){
                binding.radiosColumn1.addView(radioButton)
            } else if (index % 4 == 1) {
                binding.radiosColumn2.addView(radioButton)
            } else if (index % 4 == 2) {
                binding.radiosColumn3.addView(radioButton)
            } else  binding.radiosColumn4.addView(radioButton)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            radioButton.layoutParams = params



            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    if(checkedRadioButtonId != -1) {
                        var indexLayout = (checkedRadioButtonId % 4 )

                        val parentLayout: LinearLayout = binding.radiogroupContainer.getChildAt(indexLayout) as LinearLayout

                        for (i in 0 until parentLayout.childCount) {
                            var radidoBtn = parentLayout.getChildAt(i) as RadioButton
                            if(radidoBtn.id == checkedRadioButtonId ){
                                radidoBtn.isChecked = false
                                continue
                            }
                        }

                    }
                    checkedRadioButtonId = buttonView.id
                    selectedTime = hoursList[checkedRadioButtonId]
                    //Toast.makeText(this, "selectedTime: $selectedTime", Toast.LENGTH_SHORT).show()

                }
            }
        }


    }

    private fun clickDateButton(){
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // Check if current time is later than 16:30
        if (currentHour > 16 || (currentHour == 16 && currentMinute >= 30)) {
            // Set available day to tomorrow
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val newFragment = DatePickerFragment()
        newFragment.setDateSelectionListener(this) // Set the listener
        newFragment.setMinDate(calendar.timeInMillis) // Set the minimum date
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun clickBookAppointment() {


        var userId = sharedPreferencesHelper.getUserInfo()["userid"]

        val obj = mapOf(
            "specialistid" to selectedSpecialistId,
            "userid" to userId,
            "medicalspecialtyid" to medicalspecialtyid,
            "symptom" to "",
            "meetingtime" to selectedTime,
            "meetingdate" to selectedDate

        )


        bookApmtViewModel.bookAppointment(token, obj)
    }

    private fun showBookedAppointment(response: DataResponse) {

        if(response.status ){
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
        // get specialists by medicalspecialtyid
        lifecycleScope.launch {
            bookApmtViewModel.specialistsStateFlow.collect {
                if(it != null){
                    val specialists: List<Map<String, Any>> = it.data
                    setSpecialistSpinner(specialists)
                }
            }
        }

        // get medical specialties
        lifecycleScope.launch {
            bookApmtViewModel.medicalSpecialtiesStateFlow.collect {
                if(it != null){
                    setMedicalSpecialtySpinner(it.data)
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