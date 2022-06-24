package org.destinyardiente.tadzkiir.ui.jadwal_shalat

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
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
import kotlinx.coroutines.launch
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.core.data.DataStorePreferences
import org.destinyardiente.tadzkiir.core.data.jadwalShalatDataStore
import org.destinyardiente.tadzkiir.core.data.ltsDataStore
import org.destinyardiente.tadzkiir.core.data.source.model.JadwalShalat
import org.destinyardiente.tadzkiir.core.data.source.model.SavedJadwalShalat
import org.destinyardiente.tadzkiir.core.data.source.model.Shalat
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiConfig
import org.destinyardiente.tadzkiir.databinding.FragmentJadwalShalatBinding
import org.destinyardiente.tadzkiir.ui.jadwal_shalat.qibla.QiblaActivity
import org.destinyardiente.tadzkiir.ui.main.MainActivity
import org.destinyardiente.tadzkiir.utils.Helper.checkAndRequestPermission
import org.destinyardiente.tadzkiir.utils.Helper.enableOnClickAnimation
import org.destinyardiente.tadzkiir.utils.Helper.getCurrentDate
import org.destinyardiente.tadzkiir.utils.Helper.isOnline
import org.destinyardiente.tadzkiir.utils.Helper.isOnlineOrNot
import org.destinyardiente.tadzkiir.utils.Helper.observeOnce
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("FragmentLiveDataObserve")
class JadwalShalatFragment : Fragment() {
    companion object {
        const val REQUEST_LOCATION_PERMISSION = 10100
    }

    private var _binding: FragmentJadwalShalatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JadwalShalatViewModel by lazy {
        val api = ApiConfig.provideShalatApi
        val factory = JadwalShalatViewModelFactory(api,requireActivity().application)
        ViewModelProvider(this, factory)[JadwalShalatViewModel::class.java]
    }
    private var locationManager: LocationManager? = null

    private val jadwalShalatPref: DataStorePreferences by lazy{
        DataStorePreferences(requireActivity().jadwalShalatDataStore)
    }

    private val ltsPref: DataStorePreferences by lazy{
        DataStorePreferences(requireActivity().ltsDataStore)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getCurrentLocationIfPermissionIsGranted() {
        binding.checkLocation.isClickable = false
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        createLocationManagerReference()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJadwalShalatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isOnline(requireActivity()).observe(this){
            showDataForTheFirstTime()
            setupListeners()
            if(it==true){
                observeDay()
                setupObservers()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        observeDay()
    }


    private fun observeDay(){
        val calender: Calendar = Calendar.getInstance()
        val today = calender.get(Calendar.DAY_OF_YEAR)
        ltsPref.ltsPreferenceFlow.asLiveData()
            .observe(this){lastTimeStarted ->
                if(today!=lastTimeStarted){
                    getCurrentLocationIfPermissionIsGranted()
                    lifecycleScope.launch {
                        ltsPref.saveLtsPref(requireContext(),today)
                    }
                }
            }
    }


    private fun showDataForTheFirstTime() {
        jadwalShalatPref.jadwalShalatPreferenceFlow.asLiveData()
            .observeOnce(this){
                Log.i("isObtained",it!!.isObtained.toString())
                if(it.isObtained!=true){
                    this@JadwalShalatFragment.isOnlineOrNot {
                        binding.mainLayout.visibility = View.INVISIBLE
                        binding.shimmer.visibility = View.VISIBLE
                        getCurrentLocationIfPermissionIsGranted()
                    }
                }else {
                    val savedData = it
                    binding.tvTanggal.text = savedData.date
                    binding.tvLocation.text = savedData.location
                    binding.jadwalShalat.waktuShubuh.text = savedData.fajr
                    binding.jadwalShalat.waktuSyuruq.text = savedData.sunrise
                    binding.jadwalShalat.waktuDzuhur.text = savedData.zuhr
                    binding.jadwalShalat.waktuAshar.text = savedData.asr
                    binding.jadwalShalat.waktuMaghrib.text = savedData.maghrib
                    binding.jadwalShalat.waktuIsya.text = savedData.isha
                }
            }
    }

    private fun saveJadwalShalat(data: Shalat){
        val jadwalShalat = data.times as ArrayList<JadwalShalat>
        viewModel.location.observeOnce(this){
            Log.i("locationInfo",it)
            if(it!=null){
                binding.tvLocation.text = it
                lifecycleScope.launch {
                    jadwalShalatPref.saveJadwalShalatPref(requireContext(),
                        SavedJadwalShalat(
                            isObtained = true,
                            date =  getCurrentDate(),
                            location = it,
                            fajr = jadwalShalat[0].fajr,
                            sunrise = jadwalShalat[0].sunrise,
                            zuhr = jadwalShalat[0].zuhr,
                            asr = jadwalShalat[0].asr,
                            maghrib = jadwalShalat[0].maghrib,
                            isha = jadwalShalat[0].isha
                        )
                    )
                }
                binding.checkLocation.isClickable = true
            }
        }
    }
    private fun setupObservers() {
        viewModel.dataShalat.observeOnce(this) {
            var jadwalShalat: ArrayList<JadwalShalat> = arrayListOf()
            if (it != null) {
                jadwalShalat = it.times as ArrayList<JadwalShalat>
                Log.i("JadwalShalatFragment: ",it.toString())
                binding.tvTanggal.text = getCurrentDate()
                binding.jadwalShalat.waktuShubuh.text = jadwalShalat[0].fajr
                binding.jadwalShalat.waktuSyuruq.text = jadwalShalat[0].sunrise
                binding.jadwalShalat.waktuDzuhur.text = jadwalShalat[0].zuhr
                binding.jadwalShalat.waktuAshar.text = jadwalShalat[0].asr
                binding.jadwalShalat.waktuMaghrib.text = jadwalShalat[0].maghrib
                binding.jadwalShalat.waktuIsya.text = jadwalShalat[0].isha
                binding.shimmer.visibility = View.GONE
                binding.shimmer.stopShimmer()
                binding.mainLayout.visibility = View.VISIBLE
                saveJadwalShalat(it)
            }

        }
    }

    private val locationListener: LocationListener = object : LocationListener{

        override fun onLocationChanged(location: Location) {
            Log.i("JadwalShalatFragment", "${location.longitude}"+"${location.latitude}")
            viewModel.longitude.value = location.longitude.toString()
            viewModel.latitude.value = location.latitude.toString()
            viewModel.getJadwalShalat(viewModel.longitude.value.toString(), viewModel.latitude.value.toString())
        }

        override fun onProviderEnabled(provider: String) {
        }

        override fun onProviderDisabled(provider: String) {

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

    }
    private fun getCurrentLocation() {
        this.isOnlineOrNot {
            binding.mainLayout.visibility = View.GONE
            binding.shimmer.visibility = View.VISIBLE
            binding.shimmer.startShimmer()
            getCurrentLocationIfPermissionIsGranted()
        }
    }

    private fun setupListeners() {
        with(binding) {
            btnQiblat.enableOnClickAnimation()

            checkLocation.setOnClickListener{
                getCurrentLocation()
            }
            btnQiblat.setOnClickListener {
                startActivity(Intent(requireContext(), QiblaActivity::class.java))
                findNavController().navigate(JadwalShalatFragmentDirections.actionJadwalShalatFragmentSelf())
            }
        }
    }

    private fun createLocationManagerReference(){
        try {
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0L,0f,locationListener)
        }catch (e: SecurityException){
            Log.d("JadwalShalatFragment: ","Security Exception, no location available")
            binding.checkLocation.isClickable = true
        }
//        this.isOnlineOrNot {
//
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}