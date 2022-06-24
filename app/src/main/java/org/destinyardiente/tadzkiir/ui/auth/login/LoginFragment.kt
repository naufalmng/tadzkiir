package org.destinyardiente.tadzkiir.ui.auth.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.destinyardiente.tadzkiir.core.data.DataStorePreferences
import org.destinyardiente.tadzkiir.core.data.loginDataStore
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiConfig
import org.destinyardiente.tadzkiir.core.data.source.remote.request.LoginRequest
import org.destinyardiente.tadzkiir.core.data.usernameDataStore
import org.destinyardiente.tadzkiir.databinding.FragmentLoginBinding
import org.destinyardiente.tadzkiir.ui.auth.AuthViewModel
import org.destinyardiente.tadzkiir.ui.auth.AuthViewModelFactory
import org.destinyardiente.tadzkiir.ui.main.MainActivity
import org.destinyardiente.tadzkiir.utils.Helper.enableOnClickAnimation
import org.destinyardiente.tadzkiir.utils.Helper.isValidEmail
import org.destinyardiente.tadzkiir.utils.NetworkResult

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel : AuthViewModel by lazy {
        val api = ApiConfig.provideDzikrApi
        val factory = AuthViewModelFactory(api)
        ViewModelProvider(this,factory)[AuthViewModel::class.java]
    }
    private lateinit var loginPref: DataStorePreferences
    private lateinit var usernamePref: DataStorePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginPref = DataStorePreferences(requireContext().loginDataStore)
        usernamePref = DataStorePreferences(requireContext().usernameDataStore)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        with(binding){
            btnLogin.enableOnClickAnimation()
            btnLogin.setOnClickListener{
                setupLoginProcess()
            }
        }
        return binding.root
    }
    private fun dismiss(){
        binding.progress.isActivated = false
        binding.progress.visibility = View.GONE
        binding.btnLogin.isEnabled = true
    }
    private fun setupLoginProcess() {

        if(TextUtils.isEmpty(binding.etEmail.text)){
            Toast.makeText(requireContext(), "Email harus diisi !", Toast.LENGTH_SHORT).show()
            dismiss()
            return
        }
        if(!binding.etEmail.text.toString().isValidEmail()){
            Toast.makeText(requireContext(), "Email tidak valid !", Toast.LENGTH_SHORT).show()
            dismiss()
            return
        }
        if(TextUtils.isEmpty(binding.etPassword.text)){
            Toast.makeText(requireContext(), "Password harus diisi !", Toast.LENGTH_SHORT).show()
            dismiss()
            return
        }
        if(!TextUtils.isEmpty(binding.etEmail.text) && !TextUtils.isEmpty(binding.etPassword.text)){
            binding.progress.isActivated = true
            binding.progress.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
            viewModel.login(LoginRequest(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            ))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserToken()
     }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observeUserToken() {
        viewModel.loginRequest.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkResult.Success -> {
                    dismiss()
                    binding.etEmail.text?.clear()
                    binding.etPassword.text?.clear()
                    if(response.data !=null){
                        lifecycleScope.launch {
                            loginPref.saveLoginPref(requireContext(),response.data)
                            usernamePref.saveUsernamePref(requireContext(),response.data)
                        }
                    }
                    Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(),MainActivity::class.java))
                    requireActivity().finishAffinity()
                }
                is NetworkResult.Error -> {
                    dismiss()
                    var msg: String = response.message.toString()
                    Log.d("LoginFragment: ",response.message.toString())
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