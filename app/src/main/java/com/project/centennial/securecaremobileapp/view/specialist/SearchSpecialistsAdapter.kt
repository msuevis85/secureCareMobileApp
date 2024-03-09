package com.project.centennial.securecaremobileapp.view.specialist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.centennial.securecaremobileapp.databinding.SearchSpecialistItemViewHolderBinding

class SearchSpecialistsAdapter(private val listenerAppointment: BookSpecialistId)
    : ListAdapter<Map<String, Any>, SearchSpecialistItemViewHolder>(object : DiffUtil.ItemCallback<Map<String, Any>>() {
    override fun areItemsTheSame(oldItem: Map<String, Any>, newItem: Map<String, Any>): Boolean {
        return oldItem["specialistid"] == newItem["specialistid"]
    }

    override fun areContentsTheSame(oldItem: Map<String, Any>, newItem: Map<String, Any>): Boolean {
        return (oldItem["specialistid"].toString() == newItem["specialistid"]
                && oldItem["firstname"].toString()  == newItem["firstname"]
                && oldItem["lastname"].toString()  == newItem["lastname"])
    }
}), OnItemClickListener {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSpecialistItemViewHolder {
        context = parent.context
        val binding = SearchSpecialistItemViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SearchSpecialistItemViewHolder(binding, listenerAppointment)
    }


    override fun onBindViewHolder(holder: SearchSpecialistItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item["specialistid"].toString(),
            item["firstname"].toString(),
            item["lastname"].toString(),
            item["medialspecialty"].toString())
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    interface BookSpecialistId {
        fun onBookSpecialistById(specialistid: String)
    }

}

