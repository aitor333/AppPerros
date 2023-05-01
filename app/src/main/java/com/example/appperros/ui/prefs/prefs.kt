package com.example.appperros.ui.prefs

import android.content.Context

class prefs(val context:Context) {
    val SHARED_NAME="users_bd"
    val storage = context.getSharedPreferences(SHARED_NAME,0)
    fun saveName(name:String){
        storage.edit().putString("nombre_user",name).apply()
    }

    fun saveCorreo(name:String){
        storage.edit().putString("correo_user",name).apply()
    }

    fun getName() : String{
        return storage.getString("nombre_user","")!!
    }

    fun getCorreo() : String{
        return storage.getString("correo_user","")!!
    }
}