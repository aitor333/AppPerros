package com.example.appperros.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.appperros.databinding.ActivityUpdateBinding
import com.example.appperros.help.BdMethods.mostrarDatosPorIdEnEditTextAlActualizar
import com.example.appperros.help.Conexion
import com.example.appperros.ui.prefs.prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.PreparedStatement

class UpdateActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var prefs = prefs(this)
        val prueba = ""
        val id = prefs.getId()
        mostrarDatosPorIdEnEditTextAlActualizar(id, binding,this)
        //Actualizar perros
        actualizarBaseDeDatos(id)

        binding.btnBackInsertarUpdate.setOnClickListener {
            onBackPressed()
        }

    }

    fun actualizarBaseDeDatos(id: Int) {
        try {
            binding.btnUpdate.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val connection: Connection? = Conexion.connectionclass()

                    val query = "UPDATE perro SET nombreperro = ?,nombreraza=?," +
                            "edad=?,nchip=?,sexo=?,peso=? WHERE idperro = ?"
                    val statement: PreparedStatement = connection!!.prepareStatement(query)

                    val opcionSeleccionada = binding.spSexoUpdate.selectedItem.toString()

                    statement.setString(1, binding.edtxtNombreUpdate.text.toString())
                    statement.setString(2, binding.edtxtRazaUpdate.text.toString())
                    statement.setString(3, binding.edtxtEdadUpdate.text.toString())
                    statement.setString(4, binding.edtxtNChipUpdate.text.toString())
                    statement.setString(5, opcionSeleccionada)
                    statement.setString(6, binding.edtxtPesoUpdate.text.toString())
                    statement.setInt(7, id)


                    val filasAfectadas = statement.executeUpdate()
                    try {
                        if (filasAfectadas > 0) {
                            runOnUiThread {
                                // La actualización se realizó con éxito
                                Toast.makeText(
                                    this@UpdateActivity,
                                    "Perro actualizado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            runOnUiThread {
                                // La actualización no tuvo éxito
                                Toast.makeText(
                                    this@UpdateActivity,
                                    "Fallo al actualizar el perro",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("Msg de error al actualizar el perro ", "" + e.message)
                    }
                    statement.close()
                    connection.close()
                }

            }
        } catch (e: Exception) {
            Toast.makeText(this,"Fallo al actualizar los datos "+e.message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}