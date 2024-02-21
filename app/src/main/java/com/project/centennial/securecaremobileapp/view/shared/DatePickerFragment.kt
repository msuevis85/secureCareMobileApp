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
        return DatePickerDialog(requireContext(), this, year, month, day)

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date the user picks.
        // Call the callback method with the selected date
        dateSelectionListener?.onDateSelected(year, month, day)
    }
}