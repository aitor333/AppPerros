package com.example.appperros.ui.view


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appperros.databinding.ActivityInsertDogsBinding
import com.example.appperros.help.Conexion
import com.example.appperros.help.Conexion.connectionclass

class InsertDogsActivity : AppCompatActivity() {
    private var binding: ActivityInsertDogsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertDogsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        mostrarErrores()

        //Insert
        binding!!.btnInsertar.setOnClickListener {
            if(validarCampos()){
                Toast.makeText(applicationContext,"Fallo al rellenar los campos",Toast.LENGTH_SHORT).show()
            }


                    val connection = connectionclass()
                    try {
                        if (connection != null) {
                            val query =
                                "insert into perro (nombreperro,nombreraza,edad,nchip,sexo,peso) VALUES (?,?,?,?,?,?)"
                            val statement = connection.prepareStatement(query)
                            statement.setString(1, binding!!.edtxtNombre.text.toString())
                            statement.setString(2, binding!!.edtxtRaza.text.toString())
                            statement.setInt(3, binding!!.edtxtEdad.text.toString().toInt())
                            statement.setString(4, binding!!.edtxtNChip.text.toString())
                            statement.setString(5, binding!!.edtxtSexo.text.toString())
                            statement.setInt(6, binding!!.edtxtPeso.text.toString().toInt())
                            val rowsAffected = statement.executeUpdate()

                            Toast.makeText(
                                applicationContext,
                                "Registro insertado coreectamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            connection.close()
                        }
                    } catch (exception: Exception) {
                        Log.e("Error en el registro", exception.message!!)
                    }


            binding!!.btnBackInsertar.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun mostrarErrores() {
        var listaErrores = Conexion.errores
        for (error in listaErrores){
            if(error.equals("Network error IOException: failed to connect to /192.168.1.132 (port 1433) from /:: (port 53724): connect failed: ETIMEDOUT (Connection timed out)")){
                Toast.makeText(this@InsertDogsActivity,"Fallo en la conexion al servidor de la bd ",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarCampos() : Boolean{
        return !(binding!!.edtxtEdad.text.toString().isEmpty() && binding!!.edtxtNombre.text.toString().isEmpty() &&binding!!.edtxtRaza.text.toString().isEmpty() && binding!!.edtxtNChip.text.toString().isEmpty() && binding!!.edtxtSexo.text.toString().isEmpty() && binding!!.edtxtPeso.text.toString().isEmpty())
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
