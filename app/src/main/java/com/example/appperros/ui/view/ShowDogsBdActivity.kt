package com.example.appperros.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import com.example.appperros.databinding.ActivityShowDogsBdBinding
import com.example.appperros.help.BdMethods.getGridViewData
import com.example.appperros.help.MethodsClassics
import com.example.appperros.ui.adapter.DogBdAdapter

class ShowDogsBdActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowDogsBdBinding
    var dogAdapter: DogBdAdapter? = null
    var dogList: MutableList<String> = ArrayList()
    var metodos : MethodsClassics ?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDogsBdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding!!.btnLoadDataBDDogs.setOnClickListener {
            Looper.getMainLooper().let {
                dogList = getGridViewData()
                metodos = MethodsClassics(applicationContext,dogList)
                metodos!!.checkSizeDogList(dogList)
                dogAdapter = DogBdAdapter(this, applicationContext, dogList)
                binding!!.gridViewDogs.adapter = dogAdapter
            }
            binding!!.showDataBdBack.setOnClickListener {
                onBackPressed()
            }
        }
    }


}