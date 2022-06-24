package org.destinyardiente.tadzkiir.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.core.data.DataStorePreferences
import org.destinyardiente.tadzkiir.core.data.loginDataStore
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiConfig
import org.destinyardiente.tadzkiir.core.data.usernameDataStore
import org.destinyardiente.tadzkiir.databinding.FragmentHomeBinding
import org.destinyardiente.tadzkiir.ui.auth.AuthActivity
import org.destinyardiente.tadzkiir.ui.auth.AuthViewModel
import org.destinyardiente.tadzkiir.ui.auth.AuthViewModelFactory
import org.destinyardiente.tadzkiir.ui.home.adapter.MenuAdapter
import org.destinyardiente.tadzkiir.ui.home.dzikir.DzikirActivity
import org.destinyardiente.tadzkiir.ui.jadwal_shalat.JadwalShalatFragment
import org.destinyardiente.tadzkiir.utils.Helper.checkAndRequestPermission
import org.destinyardiente.tadzkiir.utils.Helper.isOnline

@Suppress("DEPRECATION")
@SuppressLint("FragmentLiveDataObserve")
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    private val authViewModel: AuthViewModel by lazy{
        val api = ApiConfig.provideDzikrApi
        val factory = AuthViewModelFactory(api)
        ViewModelProvider(this,factory)[AuthViewModel::class.java]
    }

    private lateinit var menuAdapter : MenuAdapter
    private val dataStorePreferences: DataStorePreferences by lazy{
        DataStorePreferences(requireContext().usernameDataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnLogout.visibility = View.INVISIBLE
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = ApiConfig.provideDzikrApi
        val factory = HomeViewModelFactory(api)
        homeViewModel=ViewModelProvider(this,factory)[HomeViewModel::class.java]
        menuAdapter = MenuAdapter(requireContext())
        requireActivity().checkAndRequestPermission(
            getString(R.string.dialog_location_title),
            getString(R.string.dialog_location_title),
            Manifest.permission.ACCESS_FINE_LOCATION,
            JadwalShalatFragment.REQUEST_LOCATION_PERMISSION,::showSuccessToast)
    }
    
    private fun showSuccessToast(){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeIsLoading()
        checkConnectivity()
        setupMenuDrawable()
     }

    private fun setupMenuDrawable() {
        menuAdapter.onItemClick
    }


    private fun checkConnectivity() {
        isOnline(requireContext().applicationContext).observe(this@HomeFragment){
            if(it == true){
                getDaftarMenu()
                setupObservers()
                setupRecyclerView()
                setupListeners()
                setupOnItemClick()
            }else{
                binding.btnLogout.visibility = View.INVISIBLE
                return@observe
            }
        }
    }

    private fun getDaftarMenu(){
      homeViewModel.getDaftarMenu()
    }

    private fun setupOnItemClick() {
        menuAdapter.onItemClick = {
            val intent = Intent(requireActivity(),DzikirActivity::class.java)
            intent.putExtra("namaDzikir",it.name)
            startActivity(intent)
        }
    }






    private fun setupListeners() {
        with(binding){
        swipeRefresh.setOnRefreshListener {
            binding.btnLogout.visibility = View.INVISIBLE
            checkConnectivity()
            observeIsLoading()
         }
            btnLogout.setOnClickListener {
                val builder: AlertDialog.Builder =
                    AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
                builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Apakah anda yakin ingin logout?</font>"))
                builder.setNegativeButton("Tidak") { dialog, i ->
                    dialog.dismiss()
                }
                builder.setPositiveButton("Ya") { dialog, i ->
                    lifecycleScope.launch {
                        dataStorePreferences.clearLoginPref(requireContext())
                        dataStorePreferences.clearJadwalShalatPref(requireContext())
                    }
                    startActivity(Intent(requireContext(),AuthActivity::class.java))
                    requireActivity().finish()
                    dialog.dismiss()
                }.create().show()
            }
        }
    }

    private fun observeIsLoading(){
        homeViewModel.isLoading.observe(this@HomeFragment) {
            if(it==true){
                binding.swipeRefresh.isRefreshing = true
                binding.shimmer.startShimmer()
                binding.shimmer.visibility = View.VISIBLE
                binding.recyclerViewMenu.visibility = View.GONE
                binding.btnLogout.visibility = View.INVISIBLE
            }
            else{
                binding.swipeRefresh.isRefreshing = false
                binding.shimmer.stopShimmer()
                binding.shimmer.visibility = View.GONE
                binding.recyclerViewMenu.visibility = View.VISIBLE
                binding.btnLogout.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMenu.apply {
            adapter = menuAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        if(view!=null){
            homeViewModel.daftarMenu.observe(this@HomeFragment){daftarMenu ->
                if (daftarMenu != null) {
                    menuAdapter.setListDaftarMenu(daftarMenu)
                    binding.btnLogout.visibility = View.VISIBLE
                }
            }
            dataStorePreferences.usernamePreferenceFlow.asLiveData()
                .observe(this@HomeFragment){name->
                    Log.i("Name: ",name.toString())
                    if(name!=null){
                        binding.username.text = getString(R.string.tv_welcome,name.toString())
                    }
                }
        }

    }

}