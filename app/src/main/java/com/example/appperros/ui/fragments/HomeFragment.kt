package com.example.appperros.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appperros.data.database.dao.UserDao
import com.example.appperros.databinding.FragmentHomeBinding
import com.example.appperros.di.AppDatabase
import com.example.appperros.ui.prefs.prefs
import com.example.appperros.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var userDao: UserDao

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

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            val prefs : prefs = prefs(requireContext())
            val nombreCorreo = prefs.getName()
            textView.text = "Nombre : "+nombreCorreo
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}