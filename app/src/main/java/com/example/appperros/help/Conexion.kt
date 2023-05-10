package com.example.appperros.help

import android.annotation.SuppressLint
import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager

object Conexion {
    @JvmStatic
    @SuppressLint("NewApi")
    fun connectionclass(): Connection? {
        var con: Connection? = null
        val ip = "192.168.1.132"
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
        } catch (exception: Exception) {
            Log.e("Error en la conexion", exception.message!!)
        }
        return con
    }
}