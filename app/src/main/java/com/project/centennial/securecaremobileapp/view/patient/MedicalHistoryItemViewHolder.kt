package com.project.centennial.securecaremobileapp.view.patient

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.MedicalHistoryItemViewHolderBinding
import com.project.centennial.securecaremobileapp.utils.AppointmentStatus
import com.project.centennial.securecaremobileapp.utils.Utilities

class MedicalHistoryItemViewHolder(
    private val binding: MedicalHistoryItemViewHolderBinding,
    private val listenerAppointment: MedicalHistoryAdapter.AppointmentHistoryId,
): RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(data: Map<String, Any>){
        Log.d("bind data  Item: ", "${data["medicalspecialty"].toString()} - ${data["status"].toString()}")
        binding.specialistNameTextview.text = "Dr. ${data["specialistfirstname"]} ${data["specialistlastname"]}"
        binding.medialSpecialtyTextview.text = data["medicalspecialty"].toString()
        binding.meetingDatetimeTextview.text = "${data["meetingdate"]} ${data["meetingtime"]}"

        binding.statusTextView.text = data["status"].toString()

        if(data["status"].toString() == AppointmentStatus.Cancel.id
            || data["status"].toString() == AppointmentStatus.Completed.id) {
            binding.cancelButton.visibility = View.GONE
        }

        if(data["status"].toString() == AppointmentStatus.Waiting.id
            || data["meetingdate"].toString() == "null"){
            binding.specialistNameTextview.text = "Not Confirmed Yet"


        }

        if(data["meetingdate"].toString() != "null"){
            binding.meetingDatetimeTextview.text = "${Utilities.formatDate(data["meetingdate"].toString())} ${data["meetingtime"]}"

        } else {
            binding.meetingDatetimeTextview.text = " - "
        }

        binding.cancelButton.setOnClickListener{
            listenerAppointment.onCancelAppointment(data["appointmentid"].toString())
        }

        binding.editButton.setOnClickListener{
            listenerAppointment.onEditAppointment(data["appointmentid"].toString(),data["status"].toString())
        }

        binding.viewDetailsButton.setOnClickListener{
            listenerAppointment.onViewAppointment(data["appointmentid"].toString())
        }
    }
}