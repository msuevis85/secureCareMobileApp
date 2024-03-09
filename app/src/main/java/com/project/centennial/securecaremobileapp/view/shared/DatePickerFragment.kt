package com.project.centennial.securecaremobileapp.view.shared

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    // Interface to communicate the selected date back to the hosting activity or fragment.
    interface DateSelectionListener {
        fun onDateSelected(year: Int, month: Int, day: Int)
    }

    private var minDate: Long = 0 // Minimum date
    // Listener reference variable
    private var dateSelectionListener: DateSelectionListener? = null

    // Method to set the listener
    fun setDateSelectionListener(listener: DateSelectionListener) {
        dateSelectionListener = listener
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        // Set the minimum date
        datePickerDialog.datePicker.minDate = minDate

        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date the user picks.
        // Call the callback method with the selected date
        dateSelectionListener?.onDateSelected(year, month, day)
    }
    fun setMinDate(minDate: Long) {
        this.minDate = minDate
    }
}