package com.project.centennial.securecaremobileapp.view.specialist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.CheckingWaitingAppointmentItemViewHolderBinding
import com.project.centennial.securecaremobileapp.utils.Utilities
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class CheckingWaitingAppointmentItemViewHolder(
    private val binding: CheckingWaitingAppointmentItemViewHolderBinding,
    private val listenerAppointment: CheckingWaitingAppointmentAdapter.WaitingAppointmentId,
): RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(data: Map<String, Any>){
        binding.meetingSymptomTextview.text = data["symptom"].toString()
        binding.createdTimeTextview.text = "${Utilities.formatDateTime(data["createdtime"].toString())}"

        binding.confirmButton.setOnClickListener{
            listenerAppointment.onConfirmAppointment(data["appointmentid"].toString())
        }
    }
}