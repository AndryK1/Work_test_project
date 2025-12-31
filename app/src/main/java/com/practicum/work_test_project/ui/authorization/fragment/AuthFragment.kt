package com.practicum.work_test_project.ui.authorization.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.practicum.work_test_project.R
import com.practicum.work_test_project.databinding.FragmentAuthorizationBinding
import com.practicum.work_test_project.ui.authorization.viewModel.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(){

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private val viewModel : AuthViewModel by viewModel()

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

        setupTextListeners()
        setupObservers()
        setupButtonListeners()
    }

    private fun setupTextListeners() {

        binding.emailInputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.passwordInputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChanged(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.isFormValid.collect { isValid ->
                binding.enterButton.isEnabled = isValid

                if (isValid) {
                    binding.enterButton.alpha = 1f
                } else {
                    binding.enterButton.alpha = 0.5f
                }
            }
        }
    }

    private fun setupButtonListeners(){
        binding.enterButton.setOnClickListener {
            if(viewModel.isFormValid.value){
                findNavController().navigate(R.id.action_authFragment_to_fragmentSearch)
            }
        }

        binding.registrationLink.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_registrationFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}