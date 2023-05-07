package com.example.appperros.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appperros.databinding.ItemDogBinding
import com.example.appperros.ui.view.ShowDogDetailsActivity
import com.squareup.picasso.Picasso

class DogViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)



    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivDog)
    }

    fun startActivity(context: Context) {
        binding.cvDog.setOnClickListener{
            val intent = Intent(context, ShowDogDetailsActivity::class.java)
            context.startActivity(intent)
        }

    }
}