package com.project.centennial.securecaremobileapp.view.specialist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.model.DataArrayResponse

class SpecialistScheduleAdapter(private var dataArrayResponse: DataArrayResponse) :
    RecyclerView.Adapter<SpecialistScheduleAdapter.SpecialistScheduleViewHolder>() {

    fun updateList(newList: DataArrayResponse) {
        dataArrayResponse = newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialistScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_specialist_schedule, parent, false)
        return SpecialistScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialistScheduleViewHolder, position: Int) {
        val specialistSchedule = dataArrayResponse.data[position]
        holder.bind(specialistSchedule)
    }

    override fun getItemCount(): Int {
        return dataArrayResponse.data.size
    }

    inner class SpecialistScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(specialistSchedule: Map<String, Any>) {
            timeTextView.text = specialistSchedule["time"] as? String
            descriptionTextView.text = specialistSchedule["description"] as? String
        }
    }
}
