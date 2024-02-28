package com.project.centennial.securecaremobileapp.view.patient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.centennial.securecaremobileapp.databinding.MedicalHistoryItemViewHolderBinding

class MedicalHistoryAdapter(private val listenerAppointment: AppointmentHistoryId)
    : ListAdapter<Map<String, Any>, MedicalHistoryItemViewHolder>(object :  DiffUtil.ItemCallback<Map<String, Any>>() {
    override fun areItemsTheSame(oldItem: Map<String, Any>, newItem: Map<String, Any>): Boolean {
        // Assuming each map has a unique identifier key like "id"
        return oldItem["appointmentid"] == newItem["appointmentid"]
    }

    override fun areContentsTheSame(oldItem: Map<String, Any>, newItem: Map<String, Any>): Boolean {
        return oldItem == newItem
    }
}), AdapterView.OnItemClickListener {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalHistoryItemViewHolder {
        context = parent.context
        val binding = MedicalHistoryItemViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MedicalHistoryItemViewHolder(binding, listenerAppointment)
    }

    override fun submitList(list: List<Map<String, Any>>?) {
        super.submitList(list?.let { ArrayList(it) })
    }


    override fun onBindViewHolder(holder: MedicalHistoryItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
    interface AppointmentHistoryId {
        fun onCancelAppointment(appointmentid: String)

        fun onEditAppointment(appointmentid: String, toString: String)

        fun onViewAppointment(appointmentid: String)
    }

}
