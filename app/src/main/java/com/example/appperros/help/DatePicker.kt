package com.example.appperros.help

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

//Esta clase recibe una funcion como parametro con el dia,la fecha y la hora
class DatePicker(val listener : (date:Int,month:Int,year:Int)->Unit)
    :DialogFragment(),DatePickerDialog.OnDateSetListener {
    //Cuando hayamos seleccionado una fecha
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth,month,year)
    }

    //Cuando haya creado el dialogo mostrara el dialogo
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day:Int = c.get(Calendar.DAY_OF_MONTH)
        val month:Int = c.get(Calendar.MONTH)
        val year:Int = c.get(Calendar.YEAR)
        val picker =  DatePickerDialog(activity as Context,this,year,month,day)
        return picker
    }

}