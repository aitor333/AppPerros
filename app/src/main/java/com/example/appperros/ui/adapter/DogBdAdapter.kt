package com.example.appperros.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.recreate
import androidx.core.app.ActivityCompat.startActivity
import androidx.fragment.app.FragmentManager
import com.example.appperros.R
import com.example.appperros.help.BdMethods
import com.example.appperros.ui.prefs.prefs
import com.example.appperros.ui.view.UpdateActivity

class DogBdAdapter(
    private val activity: Activity,
    private val context: Context,
    private val data: MutableList<String>
) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.gridview_item_dogs_bd, null)

        val textView = view.findViewById<TextView>(R.id.txtShowDogBdNombrePerro)
        textView.text = data[position]


        val button = view.findViewById<ImageButton>(R.id.deleteDogImgButton)
        button.setOnClickListener {
            val id: Int? = BdMethods.getIdByNombre(data[position])
            val nombrePerro = data[position]
            val idPerro = id


            if (id != null) {

                recreate(activity)
                BdMethods.deleteRegisterById(id)
                data.removeAt(position)
                Toast.makeText(context, "Perro borrado correctamente", Toast.LENGTH_SHORT).show()

            }
        }

        val cardViewDogBd = view.findViewById<CardView>(R.id.cardViewDogBd)
        cardViewDogBd.setOnClickListener {
            val intent = Intent(context,UpdateActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            val prefs = prefs(context)
            val id: Int? = BdMethods.getIdByNombre(data[position])
            try {
                if (id != null) {
                    prefs.saveId(id)
                    //Toast.makeText(context,"Id del perro : "+id,Toast.LENGTH_SHORT).show()
                    Log.d("Id del perro ", ""+id)
                }else{
                    Toast.makeText(context,"Fallo al identificar el perro",Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Log.e("Error al reconocer id del perro"+ id+" Message",""+e.message)
            }
        }
        return view
    }
}