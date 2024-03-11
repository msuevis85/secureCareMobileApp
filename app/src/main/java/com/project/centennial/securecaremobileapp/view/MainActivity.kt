package com.project.centennial.securecaremobileapp.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.centennial.securecaremobileapp.databinding.ActivityMainBinding
import com.project.centennial.securecaremobileapp.view.patient.BookSpecialistAppointmentActivity
import com.project.centennial.securecaremobileapp.view.shared.DrawerBaseActivity
import com.project.centennial.securecaremobileapp.view.specialist.SearchSpecialistsAdapter
import com.project.centennial.securecaremobileapp.viewmodel.SearchSpecialistViewModel
import kotlinx.coroutines.launch


class MainActivity : DrawerBaseActivity(), SearchSpecialistsAdapter.BookSpecialistId {

    private lateinit var binding: ActivityMainBinding
    private val searchViewModel: SearchSpecialistViewModel by viewModels()


    private val adapter: SearchSpecialistsAdapter by lazy {
        SearchSpecialistsAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener{onClickSearchButton()}
        binding.noDataTextview.visibility = View.GONE


        setupRecyclerView()
        listenerHandler()
    }

    private fun setupRecyclerView(){
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun onClickSearchButton(){
        var searchTerm = binding.searchTerm.text.toString()
        if(searchTerm == "") {
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
            return
        }
        searchViewModel.searchOnSpecialist(searchTerm)

    }

    private fun listenerHandler() {
        lifecycleScope.launch {
            searchViewModel.specialistsStateFlow.collect {
                if (it != null) {
                    if (it.status) {
                        Log.d("Search", " ${it.data}")

                        if (it.data.isNullOrEmpty()) {
                            // No specialists found
                            adapter.submitList(it.data)
                            binding.noDataTextview.visibility = View.VISIBLE
                            //Toast.makeText(this@MainActivity,"No specialists found", Toast.LENGTH_LONG).show()
                        } else {
                            // Specialists found, update the adapter
                            adapter.submitList(it.data)
                            binding.noDataTextview.visibility = View.GONE

                        }
                    } else {
                        Log.d("Search Error:", " ${it.message}")
                    }
                }
            }
        }
    }

    override fun onBookSpecialistById(specialistid: String) {
        val intent = Intent(this, BookSpecialistAppointmentActivity::class.java)
        intent.putExtra("SPECIALIST_ID", specialistid)
        startActivity(intent)
    }

}
