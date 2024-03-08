package com.project.centennial.securecaremobileapp.view.specialist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.centennial.securecaremobileapp.databinding.CheckingWaitingAppointmentItemViewHolderBinding

class CheckingWaitingAppointmentAdapter(private val listenerAppointment: WaitingAppointmentId)
    : ListAdapter<Map<String, Any>, CheckingWaitingAppointmentItemViewHolder>(object :  DiffUtil.ItemCallback<Map<String, Any>>() {
    override fun areItemsTheSame(oldItem: Map<String, Any>, newItem: Map<String, Any>): Boolean {
        // Assuming each map has a unique identifier key like "id"
        return oldItem["appointmentid"] == newItem["appointmentid"]
    }

    override fun areContentsTheSame(oldItem: Map<String, Any>, newItem: Map<String, Any>): Boolean {
        return oldItem == newItem
    }
}), AdapterView.OnItemClickListener {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckingWaitingAppointmentItemViewHolder {
        context = parent.context
        val binding = CheckingWaitingAppointmentItemViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CheckingWaitingAppointmentItemViewHolder(binding, listenerAppointment)
    }

    override fun submitList(list: List<Map<String, Any>>?) {
        super.submitList(list?.let { ArrayList(it) })
    }


    override fun onBindViewHolder(holder: CheckingWaitingAppointmentItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
    interface WaitingAppointmentId {
        fun onConfirmAppointment(appointmentid: String)

    }

}
