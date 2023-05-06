package com.example.appperros.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appperros.R
import com.example.appperros.databinding.FragmentHomeBinding
import com.example.appperros.ui.view.SearchDogsActivity
import com.example.appperros.ui.viewmodel.HomeViewModel
import com.example.appperros.ui.view.ShowDogsActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnVerPerros = root.findViewById<AppCompatButton>(R.id.btnVerPerros)
        val btnBuscarPerros = root.findViewById<AppCompatButton>(R.id.btnBuscarPerros)
        btnVerPerros.setOnClickListener(View.OnClickListener {
           startActivity(Intent(this@HomeFragment.requireContext(), ShowDogsActivity::class.java))
        })
        btnBuscarPerros.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@HomeFragment.requireContext(), SearchDogsActivity::class.java))
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}