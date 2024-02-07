package com.project.centennial.securecaremobileapp.view.Patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.project.centennial.securecaremobileapp.SecureCareMobileApplication
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientAddBinding
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientListBinding
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientUpdateBinding
import com.project.centennial.securecaremobileapp.viewmodel.NurseViewModel
import com.project.centennial.securecaremobileapp.viewmodel.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientUpdate : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var binding: ActivityPatientUpdateBinding
    lateinit var patientViewModel: PatientViewModel
    lateinit var nurseViewModel: NurseViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    lateinit var nurseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var patientId = intent.getStringExtra("patientId").toString()
        nurseId = intent.getStringExtra("nurseId").toString()
        var firstname = intent.getStringExtra("firstname").toString()
        var lastname = intent.getStringExtra("lastname").toString()
        var room = intent.getStringExtra("room").toString()
        var department = intent.getStringExtra("department").toString()

        val patientModelFactory =
            PatientViewModel.PatientViewModelFactory((application as SecureCareMobileApplication).patientRepository)
        patientViewModel =
            ViewModelProvider(this, patientModelFactory).get(PatientViewModel::class.java)

        val nurseModelFactory =
            NurseViewModel.NurseViewModelFactory((application as SecureCareMobileApplication).nurseRepository)
        nurseViewModel =
            ViewModelProvider(this, nurseModelFactory).get(NurseViewModel::class.java)


        binding.patientDepartment.setText(department)
        binding.patientFirstName.setText(firstname)
        binding.patientLastName.setText(lastname)
        binding.room.setText(room)
        binding.nurseIdSpinner.onItemSelectedListener = this

        lifecycle.coroutineScope.launch {
            nurseViewModel.getAllNurses().collect() {
                var arrayListNurse = ArrayList(it)
                var arrayListNurseId = arrayListNurse.map{it.nurseId}
                binding.nurseIdSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item,arrayListNurseId)
                var idPosition = arrayListNurseId.indexOf(nurseId.toInt())
                binding.nurseIdSpinner.setSelection(idPosition)
            }
        }


        binding.buttonSave.setOnClickListener {
            coroutineScope.launch {
                var patient: Patient
                patient = patientViewModel.findById(patientId.toInt())
                patient.firstname = binding.patientFirstName.text.toString()
                patient.lastname = binding.patientLastName.text.toString()
                patient.room = binding.room.text.toString().toInt()
                patient.department = binding.patientDepartment.text.toString()
                patient.nurseId = nurseId.toInt()
                patientViewModel.update(patient)
            }
            var intent = Intent(this, PatientList::class.java)
            startActivity(intent)
        }

        binding.buttonCancel.setOnClickListener {
            val intent = Intent(this, PatientList::class.java)
            startActivity(intent)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var newNurseId = parent!!.getItemAtPosition(position).toString()
        nurseId = newNurseId
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

