package com.project.centennial.securecaremobileapp.view.Patient

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.SecureCareMobileApplication
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.viewmodel.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.ParsePosition

class PatientAdapter(private val activity : PatientList) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    var patients : List<Patient> = ArrayList()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)



    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val patientName : TextView = itemView.findViewById(R.id.patientName)
        val patientDepartment : TextView = itemView.findViewById(R.id.patientDepartment)
        val patientNurse : TextView = itemView.findViewById(R.id.patientNurse)
        val patientRoom : TextView = itemView.findViewById(R.id.patientRoom)
        val patientId : TextView = itemView.findViewById(R.id.patientId)
        val cardView : CardView = itemView.findViewById(R.id.cardView)
        val testReportButton: ImageButton = itemView.findViewById(R.id.testReportButton)
        val updatePatientButton: ImageButton = itemView.findViewById(R.id.updatePatientButton)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {

        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_view_holder,parent,false)

        return PatientViewHolder(view)

    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {

        var patient : Patient = patients[position]

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        holder.patientName.text = patient.firstname + " " + patient.lastname
        holder.patientId.text = "ID: " + patient.patientId.toString()
        holder.patientNurse.text = "Nurse ID: " + patient.nurseId.toString()
        holder.patientRoom.text = "Room: " + patient.room.toString()
        holder.patientDepartment.text = "Department: " + patient.department
//        holder.deletePatientButton.tag = patient.patientId
//        holder.updatePatientButton.tag = patient.patientId
//        holder.testReportButton.tag = patient.patientId

        holder.updatePatientButton.setOnClickListener{
            val intent = Intent(activity,PatientUpdate::class.java)
            intent.putExtra("patientId", patient.patientId.toString())
            intent.putExtra("firstname", patient.firstname.toString())
            intent.putExtra("lastname", patient.lastname.toString())
            intent.putExtra("department", patient.department.toString())
            intent.putExtra("room", patient.room.toString())
            intent.putExtra("nurseId", patient.nurseId.toString())
            activity.startActivity(intent)
        }

        holder.testReportButton.setOnClickListener {
            val intent = Intent(activity, PatientTestList::class.java)
            intent.putExtra("patientId", patient.patientId.toString())
            activity.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return patients.size
    }

    fun setPatient(patient : List<Patient>){
        this.patients = patient
        notifyDataSetChanged()
    }

    fun getPatient(position : Int) : Patient {
        return patients[position]
    }


}