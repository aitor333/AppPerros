package com.example.appperros.ui.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appperros.databinding.ActivityInsertDogsBinding
import com.example.appperros.help.Conexion.connectionclass


class InsertDogsActivity : AppCompatActivity() {
    private var binding: ActivityInsertDogsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertDogsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //Insert
        binding!!.btnInsertar.setOnClickListener {
            val connection = connectionclass()
            try {
                if (connection != null) {
                    //insert into perro
                    // (nombreperro,nombreraza,edad,nchip,fecha_nacimiento,sexo,peso) values('vvv','cc',22,'vwdrB','12-12-12','m',12);
                    val query =
                        "insert into perro (nombreperro,nombreraza,edad,nchip,fecha_nacimiento,sexo,peso) VALUES (?,?,?,?,?,?,?)"
                    val statement = connection.prepareStatement(query)
                    statement.setString(1, binding!!.edtxtNombre.text.toString())
                    statement.setString(2, binding!!.edtxtRaza.text.toString())
                    statement.setInt(3, binding!!.edtxtEdad.text.toString().toInt())
                    statement.setString(4, binding!!.edtxtNChip.text.toString())
                    statement.setString(5, binding!!.edtxtFechaNacimiento.text.toString())
                    statement.setString(6, binding!!.edtxtSexo.text.toString())
                    statement.setInt(7, binding!!.edtxtPeso.text.toString().toInt())
                    val rowsAffected = statement.executeUpdate()

                    Toast.makeText(
                        this@InsertDogsActivity,
                        "Registro insertado coreectamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    connection.close()
                }
            } catch (exception: Exception) {
                Log.e("Error en el registro", exception.message!!)
            }
        }


        binding!!.btnBackInsertar.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
