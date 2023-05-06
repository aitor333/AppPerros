package com.example.appperros.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.appperros.R
import com.example.appperros.ui.prefs.prefs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001 // Código de solicitud para iniciar sesión
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInButton = findViewById<SignInButton>(R.id.google_sign_in_button)
        signInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Resultado devuelto al iniciar sesión con Google
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val nombreUser = task.result.displayName.toString()
                val correoUser = task.result.email.toString()
                // Autenticación exitosa
                GlobalScope.launch {
                    val prefs: prefs = prefs(applicationContext)
                    prefs.saveName(nombreUser)
                    prefs.saveCorreo(correoUser)
                }

                Log.d("Display Name", nombreUser)
                startActivity(Intent(this@MainActivity, BottomNavigationActivity::class.java))
                val account = task.getResult(ApiException::class.java)
                Toast.makeText(this, "Google Sign In Success", Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                // Manejar errores de inicio de sesión aquí
                Log.w("", "Google sign in failed too", e)
                Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}