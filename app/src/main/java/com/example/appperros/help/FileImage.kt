package com.example.appperros.help

import android.content.Context
import android.os.Environment
import androidx.core.content.ContentProviderCompat.requireContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FileImage(val context:Context) {
    val photoFile: File = createImageFile()
    val filePath = photoFile.path
    val absolutePathFile = photoFile.absolutePath
    val fileName = photoFile.name

    // Crea un archivo para guardar la foto

    private fun createImageFile(): File {
        // Genera un nombre único para el archivo de la foto
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"

        // Obtiene el directorio de almacenamiento externo para guardar la foto
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        // Crea el archivo de imagen
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }
}