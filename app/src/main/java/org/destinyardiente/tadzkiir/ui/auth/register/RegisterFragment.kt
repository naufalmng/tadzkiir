package org.destinyardiente.tadzkiir.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.destinyardiente.tadzkiir.core.data.DataStorePreferences
import org.destinyardiente.tadzkiir.core.data.loginDataStore
import org.destinyardiente.tadzkiir.core.data.source.remote.request.RegisterRequest
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiConfig
import org.destinyardiente.tadzkiir.core.data.source.remote.request.LoginRequest
import org.destinyardiente.tadzkiir.core.data.usernameDataStore
import org.destinyardiente.tadzkiir.databinding.FragmentRegisterBinding
import org.destinyardiente.tadzkiir.ui.auth.AuthActivity
import org.destinyardiente.tadzkiir.ui.auth.AuthViewModel
import org.destinyardiente.tadzkiir.ui.auth.AuthViewModelFactory
import org.destinyardiente.tadzkiir.ui.main.MainActivity
import org.destinyardiente.tadzkiir.utils.Helper.enableOnClickAnimation
import org.destinyardiente.tadzkiir.utils.Helper.isValidEmail
import org.destinyardiente.tadzkiir.utils.NetworkResult


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel : AuthViewModel by lazy {
        val api = ApiConfig.provideDzikrApi
        val factory = AuthViewModelFactory(api)
        ViewModelProvider(this,factory)[AuthViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        with(binding){
            btnRegister.enableOnClickAnimation()
            btnRegister.setOnClickListener{
                setupRegisterProcess()
            }
        }
        return binding.root
    }

    private fun dismiss(){
        binding.progress.visibility = View.GONE
        binding.progress.isActivated = false
        binding.btnRegister.isEnabled = true
    }

    private fun setupRegisterProcess() {
        if(TextUtils.isEmpty(binding.etEmail.text)){
            Toast.makeText(requireContext(), "Email harus diisi !", Toast.LENGTH_SHORT).show()
            return
        }
        if(!binding.etEmail.text.toString().isValidEmail()){
            Toast.makeText(requireContext(), "Email tidak valid !", Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(binding.etNama.text)){
            Toast.makeText(requireContext(), "Nama harus diisi !", Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(binding.etPassword.text)){
            Toast.makeText(requireContext(), "Password harus diisi !", Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(binding.etConfirmPassword.text)){
            Toast.makeText(requireContext(), "Konfirmasi password harus diisi !", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.etPassword.text.toString()!=binding.etConfirmPassword.text.toString()){
            Toast.makeText(requireContext(), "Password tidak sesuai dengan konfirmasi password!", Toast.LENGTH_SHORT).show()
        }
        if(binding.etConfirmPassword.text.toString()!=binding.etPassword.text.toString()){
            Toast.makeText(requireContext(), "Konfirmasi password tidak sesuai dengan password !", Toast.LENGTH_SHORT).show()
        }
        if(binding.etConfirmPassword.text?.length!! < 8 || binding.etPassword.text?.length!! < 8){
            Toast.makeText(requireContext(), "Password minimal 8 karakter !", Toast.LENGTH_SHORT).show()
        }

        if(binding.etEmail.text.toString().isValidEmail() && !TextUtils.isEmpty(binding.etPassword.text) && !TextUtils.isEmpty(binding.etConfirmPassword.text) && !TextUtils.isEmpty(binding.etNama.text) && binding.etConfirmPassword.text?.length!! > 8 || binding.etPassword.text?.length!! > 8) {
            binding.btnRegister.isEnabled = false
            binding.progress.visibility = View.VISIBLE
            binding.progress.isActivated = true
            val body = RegisterRequest(
                binding.etEmail.text.toString(),
                binding.etNama.text.toString(),
                binding.etPassword.text.toString(),
                binding.etConfirmPassword.text.toString())
            viewModel.register(body)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }
    private fun setupObservers() {
        viewModel.registerRequest.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    dismiss()
                    Toast.makeText(requireContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.login(LoginRequest(binding.etEmail.text.toString(),binding.etPassword.text.toString()))
                    binding.etEmail.text?.clear()
                    binding.etPassword.text?.clear()
                    binding.etConfirmPassword.text?.clear()
                    binding.etNama.text?.clear()
                    startActivity(Intent(requireContext(),AuthActivity::class.java))
                }
                is NetworkResult.Error -> {
                    dismiss()
                    var msg = response.message.toString()
                    if (msg.contains('{')) msg = response.message.toString()
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}