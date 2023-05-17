package com.example.appperros.ui.view


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appperros.databinding.ActivityInsertDogsBinding
import com.example.appperros.help.Conexion
import kotlinx.coroutines.*
import java.sql.Connection
import java.sql.SQLException

class InsertDogsActivity : AppCompatActivity() {
    private var binding: ActivityInsertDogsBinding? = null
    lateinit var sexoSeleccionado: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertDogsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.spSexo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                sexoSeleccionado = adapterView?.getItemAtPosition(position).toString()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    applicationContext,
                    "No se ha seleccionado ninguna opcion",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        //Insert

        binding!!.btnInsertar.setOnClickListener {


            val isValid = validarCampos(
                binding!!.edtxtNombre,
                binding!!.edtxtRaza,
                binding!!.edtxtEdad,
                binding!!.edtxtNChip,
                binding!!.edtxtPeso
            )
            if (isValid && sexoSeleccionado != null) {

                CoroutineScope(Dispatchers.IO).launch {
                    val conexion = getConexion(Conexion.connectionclass())
                    Log.d("Conexion status ",""+conexion)
                    try {
                        withContext(Dispatchers.Main) {
                            if (conexion != null) {
                                this@InsertDogsActivity.runOnUiThread {
                                    Toast.makeText(
                                        applicationContext,
                                        "Conexion Establecida ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        }

                        val insertado = insertarPerro(conexion)
                        withContext(Dispatchers.Main) {
                            try {

                                if (insertado) {
                                    this@InsertDogsActivity.runOnUiThread {
                                        Toast.makeText(
                                            applicationContext,
                                            "Perro registrado",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                            } catch (ex: SQLException) {
                                this@InsertDogsActivity.runOnUiThread {
                                    Toast.makeText(
                                        applicationContext,
                                        "Error al registrar el perro en la bd",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        }
                    } catch (ex: Exception) {
                        this@InsertDogsActivity.runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Error en la conexion : " + ex.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }



                        when (ex.message) {

                            "Network error IOException: failed to connect to /192.168.1.138 (port 1433) from /:: (port 36386): connect failed: ETIMEDOUT (Connection timed out)" ->
                                this@InsertDogsActivity.runOnUiThread {
                                    Toast.makeText(
                                        applicationContext,
                                        "Fallo en la conexion a la bd",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                }


            } else {
                // Al menos uno de los EditText es nulo o está vacío
                this@InsertDogsActivity.runOnUiThread{
                    Toast.makeText(applicationContext, "Campos Invalidos", Toast.LENGTH_SHORT).show()
                }

            }

        }
        binding!!.btnBackInsertar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validarCampos(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            val text = editText.text?.toString()
            if (text.isNullOrEmpty()) {
                return false
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    suspend fun insertarPerro(conexion: Connection?): Boolean {
        var state: Boolean = true
        try {
            if (conexion != null) {
                val query =
                    "insert into perro (nombreperro,nombreraza,edad,nchip,sexo,peso) VALUES (?,?,?,?,?,?)"
                val statement = conexion.prepareStatement(query)
                statement.setString(1, binding!!.edtxtNombre.text.toString())
                statement.setString(2, binding!!.edtxtRaza.text.toString())
                statement.setString(3, binding!!.edtxtEdad.text.toString())
                statement.setString(4, binding!!.edtxtNChip.text.toString())
                statement.setString(5, sexoSeleccionado)
                statement.setString(6, binding!!.edtxtPeso.text.toString())
                val rowsAffected = statement.executeUpdate()
                Log.d("Insert : ",""+rowsAffected)
                this@InsertDogsActivity.runOnUiThread{
                    Toast.makeText(
                        applicationContext,
                        "Registro insertado coreectamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                state = true

                conexion.close()

            } else {
                this@InsertDogsActivity.runOnUiThread{
                    Toast.makeText(
                        this@InsertDogsActivity,
                        "Fallo en la conexion al servidor de la bd ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state = false
            }
        } catch (exception: SQLException) {
            Log.e("Error en el registro", exception.message!!)
            state = false

        }

        return state
    }

    suspend fun getConexion(conexion: Connection?): Connection? {
        return conexion
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
