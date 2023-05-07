package com.example.appperros.ui.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.example.appperros.databinding.ActivityShowDogDetailsBinding

class ShowDogDetailsActivity : AppCompatActivity() {
    lateinit var binding : ActivityShowDogDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDogDetailsBinding.inflate(layoutInflater)
        setProgressBarMine(binding.prgBarShowDogs)
        setContentView(binding.root)
        binding.btnAtras.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setProgressBarMine(pr : ProgressBar) {

        Thread {
            // Verificar la conexión a internet
            val isInternetAvailable = isInternetAvailable()

            runOnUiThread {
                // Si hay conexión, ocultar la ProgressBar y hacer cualquier otra acción necesaria
                if (isInternetAvailable) {

                    //Mostramos la informacion en el textview del nombre raza
                    binding.txtContentNombreRaza.text = "Nombre Raza : hound"

                }
                showProgressBar(pr)
                Thread.sleep(2000)
                hideProgressBar(pr)
            }
        }.start()
    }

    //Metodo que verifica si hay conexion a internet
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    //Metodo que esconde la progressBar
    private fun hideProgressBar(pr : ProgressBar) {
        pr.visibility = View.GONE
    }

    //Metodo que muestra la progressBar
    private fun showProgressBar(pr : ProgressBar) {
        pr.visibility = View.INVISIBLE
    }

}