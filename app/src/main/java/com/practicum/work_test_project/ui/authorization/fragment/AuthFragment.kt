package com.practicum.work_test_project.ui.authorization.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.practicum.work_test_project.R
import com.practicum.work_test_project.databinding.FragmentAuthorizationBinding

class AuthFragment : Fragment(){

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterButton.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_fragmentSearch)
        }

        binding.registrationLink.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_registrationFragment)

            findNavController().popBackStack(R.id.registrationFragment, true)
        }
    }
}