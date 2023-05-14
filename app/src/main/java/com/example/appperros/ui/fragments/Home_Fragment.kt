package com.example.appperros.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import com.example.appperros.R
import com.example.appperros.databinding.FragmentHomeBinding
import com.example.appperros.databinding.FragmentUserBinding
import com.example.appperros.help.Conexion
import com.example.appperros.ui.prefs.prefs
import com.example.appperros.ui.view.InsertDogsActivity
import com.example.appperros.ui.view.SearchDogsActivity
import com.example.appperros.ui.view.ShowDogsActivity
import com.example.appperros.ui.view.ShowDogsBdActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection

class Home_Fragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var conexion: Connection? = null
    var adapter: SimpleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = prefs(requireContext())

         binding.btnInsertarPerro.setOnClickListener {
             startActivity(Intent(requireContext(),InsertDogsActivity::class.java))
         }

        binding.btnVerPerrosBd.setOnClickListener{
            startActivity(Intent(requireContext(),ShowDogsBdActivity::class.java))
        }

        binding.btnVerPerrosBd.setOnClickListener {
            startActivity(Intent(requireContext(),ShowDogsBdActivity::class.java))
        }
        binding.btnVerPerros.setOnClickListener(View.OnClickListener {
            startActivity(Intent(requireContext(), ShowDogsActivity::class.java))
        })
        binding.btnBuscarPerros.setOnClickListener(View.OnClickListener {
            startActivity(Intent(requireContext(), SearchDogsActivity::class.java))
        })
    }
}