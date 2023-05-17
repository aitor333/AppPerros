package com.example.appperros.help

import android.content.Context
import android.util.Log
import android.widget.Toast

class MethodsClassics(val context: Context,val lista : MutableList<String>) {
    fun checkSizeDogList(lista: List<String>) {
        if (lista.isEmpty()) {
            Toast.makeText(
                context,
                "No hay perros en la base de datos de oracle ",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Log.d("Perros Size", "Hay ${lista.size} perros en la base de datos de oracle")
        }
    }
}