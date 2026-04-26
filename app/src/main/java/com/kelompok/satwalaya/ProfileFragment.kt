package com.kelompok.satwalaya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kelompok.satwalaya.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProfileName.text = "Alexander"
        binding.tvProfileEmail.text = "alexander@gmail.com"
        binding.tvInfoName.text = "Alexander"
        binding.tvInfoEmail.text = "alexander@gmail.com"
        binding.tvInfoPhone.text = "+62 888-8888-8888"

        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur edit profil", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Berhasil keluar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}