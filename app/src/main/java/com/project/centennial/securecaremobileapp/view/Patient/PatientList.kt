package com.project.centennial.securecaremobileapp.view.Patient

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.centennial.securecaremobileapp.SecureCareMobileApplication
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.databinding.ActivityPatientListBinding
import com.project.centennial.securecaremobileapp.view.MainActivity
import com.project.centennial.securecaremobileapp.view.Patient.PatientAdapter
import com.project.centennial.securecaremobileapp.viewmodel.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientList : AppCompatActivity() {

    lateinit var binding: ActivityPatientListBinding
    lateinit var patientViewModel: PatientViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val patientAdapter = PatientAdapter(this)
        recyclerView.adapter = patientAdapter


        val patientModelFactory =
            PatientViewModel.PatientViewModelFactory((application as SecureCareMobileApplication).patientRepository)
        patientViewModel =
            ViewModelProvider(this, patientModelFactory).get(PatientViewModel::class.java)

        binding.addNewButton.setOnClickListener {
            val intent = Intent(this, PatientAdd::class.java)
            startActivity(intent)
        }

        binding.goBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        lifecycle.coroutineScope.launch {
            patientViewModel.getAllPatient().collect() {
                patientAdapter.setPatient(it)
            }
        }



            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    coroutineScope.launch {
                        val patientId =
                            patientAdapter.getPatient(viewHolder.adapterPosition).patientId;
                        val patient = patientViewModel.findById(patientId);
                        Log.e("TOMALO", "" + patient.toString())
                        patientViewModel.delete(patient)

                        Snackbar.make(recyclerView, "Deleted " + patient.firstname, Snackbar.LENGTH_LONG)
                            .setAction(
                                "Undo",
                                View.OnClickListener {
                                    patientViewModel.insert(patient)
                                }
                            )
                            .show()
                    }
                }

            }).attachToRecyclerView(recyclerView)


        }
    }
