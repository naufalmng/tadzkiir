package org.destinyardiente.tadzkiir.ui.jadwal_shalat

import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.core.data.repository.AppRepository
import org.destinyardiente.tadzkiir.core.data.source.model.JadwalShalat
import org.destinyardiente.tadzkiir.core.data.source.model.SavedJadwalShalat
import org.destinyardiente.tadzkiir.core.data.source.model.Shalat
import org.destinyardiente.tadzkiir.core.data.source.remote.RemoteDataSource
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService
import java.util.*
import kotlin.collections.HashMap

class JadwalShalatViewModel(remote: ApiService,private val applicationContext: Application? = null): ViewModel() {
    private val repo = AppRepository(RemoteDataSource(remote))
    private var _dataShalat = MutableLiveData<Shalat>()
    val dataShalat: LiveData<Shalat> get() = (_dataShalat)

    var latitude = MutableLiveData<String>()
    var longitude = MutableLiveData<String>()
    private var _location = MutableLiveData<String>()
    val location: LiveData<String> get() = (_location)
    var savedJadwalShalat =  MutableLiveData<SavedJadwalShalat?>()

    private var _jadwalShalat = MutableLiveData<List<JadwalShalat>?>()

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = (_isLoading)


    private fun getCurrentAddress(long: Double,lat: Double){
        if(applicationContext!=null){
            val geoCoder = Geocoder(applicationContext, Locale.getDefault())
            val addresses = geoCoder.getFromLocation(lat,long,1)
            val cityName = addresses[0].subAdminArea
            val stateName = addresses[0].adminArea
            Log.i("locationInfo","city: $cityName")
            _location.postValue(applicationContext.getString(R.string.location,cityName,stateName))
//            _dataShalat.value?.state = stateName
//            savedJadwalShalat.value?.city = cityName
//            savedJadwalShalat.value?.state = stateName
        }
    }


    fun getJadwalShalat(longitude: String?,latitude: String?){
        val location: MutableMap<String?, String?> = HashMap()
        location["longitude"] = longitude
        location["latitude"] = latitude

        viewModelScope.launch(Dispatchers.IO){
            _isLoading.postValue(true)
            val response = repo.remote.getJadwalShalat(location)
            if(response.isSuccessful){
                Log.i("Shalat: ",response.body().toString())
                response.body()?.let {
                    _dataShalat.postValue(it)
                    Log.i("Shalat: ",it.toString())
                    _jadwalShalat.postValue(it.times)
                    savedJadwalShalat.value?.fajr = it.times?.get(0)?.fajr
                    savedJadwalShalat.value?.sunrise = it.times?.get(0)?.sunrise
                    savedJadwalShalat.value?.zuhr = it.times?.get(0)?.zuhr
                    savedJadwalShalat.value?.asr = it.times?.get(0)?.asr
                    savedJadwalShalat.value?.maghrib = it.times?.get(0)?.maghrib
                    savedJadwalShalat.value?.isha = it.times?.get(0)?.isha
                    getCurrentAddress(it.longitude.toString().toDouble(),it.latitude.toString().toDouble())
                    _isLoading.postValue(false)
                }
            }
        }
    }
}