package com.project.centennial.securecaremobileapp.view.specialist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.CheckingWaitingAppointmentItemViewHolderBinding
import com.project.centennial.securecaremobileapp.utils.Utilities
import java.time.LocalTime
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
        binding.createdTimeTextview.text = "${Utilities.formatDate(data["createdtime"].toString())}"
        //binding.createdTimeTextview.text = "${data["createdtime"].toString()}"
        Log.d("CheckingWaitingAppointmentItemViewHolder: ", data.toString())
        binding.confirmButton.setOnClickListener{

            var time = Utilities.getTimeDate(10);
            var date = Utilities.getFormattedDate()
            val body = mapOf(
                "appointmentid" to data["appointmentid"].toString(),
                "meetingdate" to date,
                "meetingtime" to time
            )

            listenerAppointment.onConfirmAppointment(body)
        }
    }
}