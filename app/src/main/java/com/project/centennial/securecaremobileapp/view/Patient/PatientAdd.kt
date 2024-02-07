package com.project.centennial.securecaremobileapp.view.Patient

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.project.centennial.securecaremobileapp.SecureCareMobileApplication
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientAddBinding
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientUpdateBinding
import com.project.centennial.securecaremobileapp.viewmodel.NurseViewModel
import com.project.centennial.securecaremobileapp.viewmodel.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientAdd : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityPatientAddBinding
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var patientViewModel : PatientViewModel
    lateinit var nurseViewModel: NurseViewModel
    lateinit var nurseId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientAddBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val patientModelFactory = PatientViewModel.PatientViewModelFactory((application as SecureCareMobileApplication).patientRepository)
        patientViewModel = ViewModelProvider(this, patientModelFactory).get(PatientViewModel::class.java)

        val nurseModelFactory =
            NurseViewModel.NurseViewModelFactory((application as SecureCareMobileApplication).nurseRepository)
        nurseViewModel =
            ViewModelProvider(this, nurseModelFactory).get(NurseViewModel::class.java)

        binding.nurseIdSpinner.onItemSelectedListener = this

        lifecycle.coroutineScope.launch {
            nurseViewModel.getAllNurses().collect() {
                var arrayListNurse = ArrayList(it)
                var arrayListNurseId = arrayListNurse.map{it.nurseId}
                binding.nurseIdSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item,arrayListNurseId)
//                var idPosition = arrayListNurseId.indexOf(0)
//                binding.nurseIdSpinner.setSelection(idPosition)
            }
        }

        binding.buttonSave.setOnClickListener {

            var patient = Patient(0,binding.patientFirstName.text.toString(),binding.patientLastName.text.toString(),binding.patientDepartment.text.toString(),nurseId.toInt(),binding.room.text.toString().toInt())
            coroutineScope.launch{
                patientViewModel.insert(patient)
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

