package com.project.centennial.securecaremobileapp.view.Test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.project.centennial.securecaremobileapp.SecureCareMobileApplication
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Test
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientAddBinding
import com.project.centennial.securecaremobileapp.databinding.ActivityTestAddBinding
import com.project.centennial.securecaremobileapp.view.MainActivity
import com.project.centennial.securecaremobileapp.view.Patient.PatientList
import com.project.centennial.securecaremobileapp.view.Patient.PatientTestList
import com.project.centennial.securecaremobileapp.viewmodel.NurseViewModel
import com.project.centennial.securecaremobileapp.viewmodel.PatientViewModel
import com.project.centennial.securecaremobileapp.viewmodel.TestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestAdd : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityTestAddBinding
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var patientViewModel : PatientViewModel
    lateinit var nurseViewModel: NurseViewModel
    lateinit var testViewModel: TestViewModel
    lateinit var nurseId : String
    lateinit var patientId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myPreference = getSharedPreferences("session", MODE_PRIVATE)
        val nurseIdPref = myPreference.getString("nurseId",null)

        binding.nurseId.text = "Nurse ID: " + nurseIdPref

        val patientModelFactory = PatientViewModel.PatientViewModelFactory((application as SecureCareMobileApplication).patientRepository)
        patientViewModel = ViewModelProvider(this, patientModelFactory).get(PatientViewModel::class.java)

        val nurseModelFactory = NurseViewModel.NurseViewModelFactory((application as SecureCareMobileApplication).nurseRepository)
        nurseViewModel = ViewModelProvider(this, nurseModelFactory).get(NurseViewModel::class.java)

        val testModelFactory = TestViewModel.TestViewModelFactory((application as SecureCareMobileApplication).testRepository)
        testViewModel = ViewModelProvider(this, testModelFactory).get(TestViewModel::class.java)

        binding.patientIdSpinner.onItemSelectedListener = this


        lifecycle.coroutineScope.launch {
            patientViewModel.getAllPatient().collect() {
                var arrayListPatient = ArrayList(it)
                var arrayListPatientId = arrayListPatient.map{it.patientId}
                binding.patientIdSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item,arrayListPatientId)
            }
        }



        binding.buttonSave.setOnClickListener {
            var bpl = binding.bplNumber.text.toString().toDouble()
            var bph = binding.bphNumber.text.toString().toDouble()
            var temp = binding.temperature.text.toString().toDouble()

            var test = Test(0,patientId.toInt(),nurseIdPref!!.toInt(),bpl,bph,temp)
            coroutineScope.launch{
                testViewModel.insert(test)
            }
            Toast.makeText(this,"Test created successful",Toast.LENGTH_LONG).show()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCancel.setOnClickListener {
            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var patientIdSelected = parent!!.getItemAtPosition(position).toString()
        patientId = patientIdSelected    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}