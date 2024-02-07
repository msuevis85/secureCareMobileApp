package com.project.centennial.securecaremobileapp.view.Patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.SecureCareMobileApplication
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientListBinding
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientTestListBinding
import com.project.centennial.securecaremobileapp.view.MainActivity
import com.project.centennial.securecaremobileapp.view.Test.TestAdapter
import com.project.centennial.securecaremobileapp.viewmodel.PatientViewModel
import com.project.centennial.securecaremobileapp.viewmodel.TestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientTestList : AppCompatActivity() {

    private lateinit var binding: ActivityPatientTestListBinding
    private lateinit var testViewModel: TestViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBack.setOnClickListener {
            val intent = Intent(this, PatientList::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val testAdapter = TestAdapter(this)
        recyclerView.adapter = testAdapter

        var patientId = intent.getStringExtra("patientId")!!

        binding.patientId.text = "Patient ID: " + patientId

        val testModelFactory = TestViewModel.TestViewModelFactory((application as SecureCareMobileApplication).testRepository)
        testViewModel = ViewModelProvider(this, testModelFactory).get(TestViewModel::class.java)


        lifecycle.coroutineScope.launch {
            testViewModel.getAllPatientTest(patientId.toInt()).collect() {
                testAdapter.setTest(it)
            }
        }
    }
}