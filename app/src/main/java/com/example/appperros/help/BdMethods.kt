package com.example.appperros.help

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.appperros.databinding.ActivityUpdateBinding
import com.example.appperros.ui.prefs.prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import com.example.appperros.help.Conexion as Conexion

object BdMethods {
    fun getIdByNombre(nombre: String): Int? {
        val connection = Conexion.connectionclass()
        val query = "SELECT idperro FROM perro WHERE nombreperro = ?"
        val statement = connection!!.prepareStatement(query)
        statement.setString(1, nombre)
        val resultSet: ResultSet = statement.executeQuery()

        var id: Int? = null
        if (resultSet.next()) {
            id = resultSet.getInt("idperro")
        }
        connection.close()
        return id
    }

    fun deleteRegisterById(id: Int) {
        val query = "DELETE FROM perro WHERE idperro = $id"
        val statement = Conexion.connectionclass()?.createStatement()
        statement?.executeUpdate(query)
        statement?.close()
    }

    fun getGridViewData(): MutableList<String> {
        val data = mutableListOf<String>()
        val query = "SELECT * FROM perro"
        val statement = Conexion.connectionclass()?.createStatement()
        val resultSet: ResultSet = statement!!.executeQuery(query)

        while (resultSet.next()) {
            val value = resultSet.getString("nombreperro")
            data.add(value)
        }
        return data
    }

    fun mostrarDatosPorIdEnEditTextAlActualizar(id: Int,binding:ActivityUpdateBinding,context:Context){
        try {
            val connection: Connection? = Conexion.connectionclass()
            val statement = connection?.createStatement()

            val query = "SELECT * FROM perro WHERE idperro = $id"
            val resultSet: ResultSet = statement!!.executeQuery(query)

            while (resultSet.next()) {
                val nombrePerro = resultSet.getString("nombreperro")
                val nombreRaza = resultSet.getString("nombreraza")
                val edadPerro = resultSet.getString("edad")
                val nChipPerro = resultSet.getString("nchip")
                val nombreSexo = resultSet.getString("sexo")
                val pesoPerro = resultSet.getString("peso")

                val prefs = prefs(context)
                prefs.saveNombreSexo(nombreSexo.toString())

                binding.edtxtNombreUpdate.setText(nombrePerro.toString())
                binding.edtxtRazaUpdate.setText(nombreRaza.toString())
                binding.edtxtEdadUpdate.setText(edadPerro.toString())
                binding.edtxtNChipUpdate.setText(nChipPerro.toString())
                var opcionSeleccionada : Int
                Log.d("Nombre sexo perro ",nombreSexo.toString())
                when(nombreSexo.toString()){
                    "M"-> opcionSeleccionada=0
                    "F"->opcionSeleccionada=1
                    else-> opcionSeleccionada=0
                }
                binding.spSexoUpdate.setSelection(opcionSeleccionada)
                Log.d("Opcion seleccionada en el spinner de sexo del perro al actualizar el perro ",""+opcionSeleccionada)
                binding.edtxtPesoUpdate.setText(pesoPerro.toString())
            }

            resultSet.close()
            statement.close()
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}