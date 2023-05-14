package com.example.appperros.ui.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appperros.R
import com.example.appperros.databinding.FragmentUserBinding
import com.example.appperros.ui.prefs.prefs
import android.Manifest
import com.example.appperros.help.Conexion
import com.example.appperros.help.FileImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.sql.Connection
import java.sql.SQLException


class UserFragment : Fragment(R.layout.fragment_user) {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    companion object {

        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            //Imagen seleccionada
            binding.imgProfileUser.setImageURI(uri);
            Log.d("Uri foto : ", "" + uri)
            Toast.makeText(requireContext(), "Imagen de perfil actualizada", Toast.LENGTH_SHORT)
                .show()
        } else {
            //Imagen no seleccionada
            Toast.makeText(
                requireContext(),
                "Fallo al actualizar la nueva foto de perfil",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = prefs(requireContext())

        binding.txtUsernameGoogle.setText(prefs.getName())

        binding.imgProfileUser.setOnClickListener {
            checkPermissions()
        }
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

            result : ActivityResult->

        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val imageBitMap = intent?.extras?.get("data") as Bitmap
            binding.imgProfileUser.setImageBitmap(imageBitMap)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            data?.extras?.let { bundle->{
                val imageBitMap = bundle.get("data") as Bitmap
                binding.imgProfileUser.setImageBitmap(imageBitMap)
            }
            }
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //Permiso no aceptado por el momento
            requestCameraPermission()
        } else {
            // El permiso de la cámara ya está concedido, puedes realizar la acción deseada
            // Por ejemplo, abrir la cámara
            Toast.makeText(requireContext(),"Abriendo la camara ...",Toast.LENGTH_SHORT).show()
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            CoroutineScope(Dispatchers.IO).launch {
                val conexion = getConexion(Conexion.connectionclass())
                Log.d("Conexion status ",""+conexion)
                try {
                    withContext(Dispatchers.Main) {
                        if (conexion != null) {
                            requireActivity().runOnUiThread {
                                Toast.makeText(
                                    requireContext(),
                                    "Conexion Establecida ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }

                    val insertado = insertarFotoPerfil(conexion)
                    withContext(Dispatchers.Main) {
                        try {

                            if (insertado) {
                                requireActivity().runOnUiThread {
                                    Toast.makeText(
                                        requireContext(),
                                        "Perro registrado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        } catch (ex: SQLException) {
                            requireActivity().runOnUiThread {
                                Toast.makeText(
                                    requireContext(),
                                    "Error al registrar el perro en la bd",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                } catch (ex: Exception) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            "Error en la conexion : " + ex.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }



                    when (ex.message) {

                        "Network error IOException: failed to connect to /192.168.1.138 (port 1433) from /:: (port 36386): connect failed: ETIMEDOUT (Connection timed out)" ->
                            requireActivity().runOnUiThread {
                                Toast.makeText(
                                    requireContext(),
                                    "Fallo en la conexion a la bd",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
            }

        }
    }

    private fun requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.CAMERA)){
            //El usuario ya ha rechazado los permisos debemos informarle que vaya a ajustes
        }else{
            //Pedir permisos
            ActivityCompat.requestPermissions(requireActivity(), arrayOf( Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //El usuario ha aceptado el permiso, no tiene porqué darle de nuevo al botón, podemos lanzar la funcionalidad desde aquí.
                    startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                    val conexion = Conexion.connectionclass()
                    CoroutineScope(Dispatchers.IO).launch {
                        val conexion = getConexion(Conexion.connectionclass())
                        Log.d("Conexion status ",""+conexion)
                        try {
                            withContext(Dispatchers.Main) {
                                if (conexion != null) {
                                    requireActivity().runOnUiThread {
                                        Toast.makeText(
                                            requireContext(),
                                            "Conexion Establecida ",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                            }

                            val insertado = insertarFotoPerfil(conexion)
                            withContext(Dispatchers.Main) {
                                try {

                                    if (insertado) {
                                        requireActivity().runOnUiThread {
                                            Toast.makeText(
                                                requireContext(),
                                                "Perro registrado",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                } catch (ex: SQLException) {
                                    requireActivity().runOnUiThread {
                                        Toast.makeText(
                                            requireContext(),
                                            "Error al registrar el perro en la bd",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                            }
                        } catch (ex: Exception) {
                            requireActivity().runOnUiThread {
                                Toast.makeText(
                                    requireContext(),
                                    "Error en la conexion : " + ex.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }



                            when (ex.message) {

                                "Network error IOException: failed to connect to /192.168.1.138 (port 1433) from /:: (port 36386): connect failed: ETIMEDOUT (Connection timed out)" ->
                                    requireActivity().runOnUiThread {
                                        Toast.makeText(
                                            requireContext(),
                                            "Fallo en la conexion a la bd",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    }
                } else {
                    //El usuario ha rechazado el permiso, podemos desactivar la funcionalidad o mostrar una vista/diálogo.
                    Toast.makeText(
                        requireContext(),
                        "Permiso de camara denegado",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                return
            }
            else -> {
                // Este else lo dejamos por si sale un permiso que no teníamos controlado.
                Toast.makeText(requireContext(), "Permiso de camara denegadi", Toast.LENGTH_SHORT)
                    .show()

            }
        }

    }


    suspend fun insertarFotoPerfil(conexion: Connection?): Boolean {
        var state: Boolean = true
        var rutaArchivo = FileImage(requireContext()).absolutePathFile
        try {
            if (conexion != null) {
                val query =
                    "insert into photo (imgperfil) VALUES (?)"
                val statement = conexion.prepareStatement(query)
                statement.setString(1,rutaArchivo)
                val rowsAffected = statement.executeUpdate()
                Log.d("Insert : ",""+rowsAffected)
                requireActivity().runOnUiThread{
                    Toast.makeText(
                        requireContext(),
                        "Registro insertado coreectamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state = true
                conexion.close()

            } else {
                requireActivity().runOnUiThread{
                    Toast.makeText(
                        requireContext(),
                        "Fallo en la conexion al servidor de la bd ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state = false
            }
        } catch (exception: SQLException) {
            requireActivity().runOnUiThread{
                Toast.makeText(
                    requireContext(),
                    "Error en la inserccion de la foto de perfil a la bd:  "+exception.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            state = false

        }

        return state
    }

    suspend fun getConexion(conexion: Connection?): Connection? {
        return conexion
    }

    private fun elegirFoto() {
        val imageUri = "image/*";
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}