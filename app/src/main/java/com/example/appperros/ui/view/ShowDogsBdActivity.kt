package com.example.appperros.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter
import android.widget.Toast
import com.example.appperros.R
import com.example.appperros.databinding.ActivityShowDogsBdBinding
import com.example.appperros.databinding.ActivityShowDogsBinding
import com.example.appperros.help.Conexion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection

class ShowDogsBdActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowDogsBdBinding
    var conexion: Connection? = null
    var adapter: SimpleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDogsBdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding!!.btnLoadDataBDDogs.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val data: MutableList<Map<String, String?>> = ArrayList()
                try {
                    conexion = Conexion.connectionclass()
                    if (conexion != null) {
                        val query = "select * from perro"
                        val st = conexion!!.createStatement()
                        val rs = st.executeQuery(query)
                        while (rs.next()) {
                            val list: MutableMap<String, String?> = HashMap()
                            list["NombrePerro"] = rs.getString("nombreperro")
                            list["EdadPerro"] = rs.getString("edad")
                            data.add(list)
                        }
                        val from = arrayOf("NombrePerro", "EdadPerro")
                        val to =
                            intArrayOf(R.id.txtShowDogBdNombrePerro, R.id.txtShowDogBdNombreRaza)
                        adapter = SimpleAdapter(
                            this@ShowDogsBdActivity,
                            data,
                            R.layout.gridview_item_dogs_bd,
                            from,
                            to
                        )

                        withContext(Dispatchers.Main){
                            runOnUiThread {
                                binding!!.gridViewDogs.adapter = adapter
                            }
                        }
                    }
                } catch (ex: Exception) {
                    Log.e("Error en la conexion", ex.message!!)

                }

            }
        }


        binding!!.showDataBdBack.setOnClickListener {
            onBackPressed()
        }

    }
}