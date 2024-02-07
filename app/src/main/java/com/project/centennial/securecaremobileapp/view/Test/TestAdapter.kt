package com.project.centennial.securecaremobileapp.view.Test

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Test
import com.project.centennial.securecaremobileapp.view.Patient.PatientTestList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TestAdapter(private val activity: PatientTestList) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

        var tests: List<Test> = ArrayList()
        private val coroutineScope = CoroutineScope(Dispatchers.IO)


        class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val patientId: TextView = itemView.findViewById(R.id.patientId)
            val nurseId: TextView = itemView.findViewById(R.id.nurseId)
            val testId: TextView = itemView.findViewById(R.id.testId)
            val bphNumber: TextView = itemView.findViewById(R.id.bphNumber)
            val bplNumber: TextView = itemView.findViewById(R.id.bplNumber)
            val temperature: TextView = itemView.findViewById(R.id.temperature)
            val cardView: CardView = itemView.findViewById(R.id.cardView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.test_view_holder, parent, false)

            return TestViewHolder(view)

        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            var test: Test = tests[position]
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            holder.testId.text = "Test ID: " + test.testId
//            holder.patientId.text = "Patient ID: " + test.patientId
            holder.nurseId.text = "Nurse ID: " + test.nurseId
            holder.bplNumber.text = "BPL: " + test.BPL
            holder.bphNumber.text = "BPH: " + test.BPH
            holder.temperature.text = "Temperature: " + test.temperature
        }

        override fun getItemCount(): Int {
            return tests.size
        }

        fun setTest(test: List<Test>) {
            this.tests = test
            notifyDataSetChanged()
        }

        fun getTest(position: Int): Test {
            return tests[position]
        }


    }
