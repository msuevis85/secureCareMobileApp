package com.project.centennial.securecaremobileapp.view.specialist

import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.SearchSpecialistItemViewHolderBinding

class SearchSpecialistItemViewHolder(
    private val binding: SearchSpecialistItemViewHolderBinding,
    private val listenerAppointment: SearchSpecialistsAdapter.BookSpecialistId,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(specialistid: String,
             firstname: String,
             lastname: String,
             medialspecialty: String){

        binding.specialistNameTextview.text = "Dr. ${firstname} ${lastname}"
        binding.medialSpecialtyTextview.text = medialspecialty
        binding.appointmentButton.setOnClickListener{
            listenerAppointment.onBookSpecialistById(specialistid)
        }
    }
}