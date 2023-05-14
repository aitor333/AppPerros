package com.example.appperros.help

import android.annotation.SuppressLint
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Conexion {

    var errores = mutableListOf<String>()
    @JvmStatic
    @SuppressLint("NewApi")
    fun connectionclass(): Connection? {
        var con: Connection? = null
        val ip = "192.168.1.138"
        val port = "1433"
        val username = "aitor"
        val password = "aitor2002"
        val databasename = "bd_perros"
        val tp = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(tp)
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            val connectionUrl =
                "jdbc:jtds:sqlserver://$ip:$port;databasename=$databasename;User=$username;password=$password;"
            con = DriverManager.getConnection(connectionUrl)
        } catch (exception: SQLException) {
            Log.e("Error en la conexion", exception.message!!)
            when(exception.message){
                "Network error IOException: failed to connect to /192.168.1.132 (port 1433) from /:: (port 53724): connect failed: ETIMEDOUT (Connection timed out)"
                -> errores.add(0,"Network error IOException: failed to connect to /192.168.1.132 (port 1433) from /:: (port 53724): connect failed: ETIMEDOUT (Connection timed out)")
            }
        }
        return con
    }
}