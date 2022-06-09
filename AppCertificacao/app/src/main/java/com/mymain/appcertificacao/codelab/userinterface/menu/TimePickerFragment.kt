package com.mymain.appcertificacao.codelab.userinterface.menu

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.util.Util
import java.text.SimpleDateFormat
import java.util.*

class TimePickerFragment(val callback: (result: String) -> Unit) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        return TimePickerDialog(
            requireContext(),
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {

        val hourString = hour.toString()
        val minuteString = minute.toString()
        callback("$hourString : $minuteString")
    }
}