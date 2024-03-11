package com.project.centennial.securecaremobileapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.ItemSpecialistScheduleBinding

class SpecialistScheduleAdapter(private var dataArrayResponse: List<Map<String, Any>>) :
    RecyclerView.Adapter<SpecialistScheduleAdapter.SpecialistScheduleViewHolder>() {

    fun updateList(newList: List<Map<String, Any>>) {
        dataArrayResponse = newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialistScheduleViewHolder {
        val binding = ItemSpecialistScheduleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SpecialistScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecialistScheduleViewHolder, position: Int) {
        val specialistSchedule = dataArrayResponse[position]
        holder.bind(specialistSchedule)
    }

    override fun getItemCount(): Int {
        return dataArrayResponse.size
    }

    inner class SpecialistScheduleViewHolder(private val binding: ItemSpecialistScheduleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(specialistSchedule: Map<String, Any>) {
            binding.timeTextView.text = specialistSchedule["meetingtime"] as? String
            binding.descriptionTextView.text = specialistSchedule["symptom"] as? String
        }
    }
}