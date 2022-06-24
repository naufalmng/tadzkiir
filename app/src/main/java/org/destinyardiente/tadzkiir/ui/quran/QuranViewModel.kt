package org.destinyardiente.tadzkiir.ui.quran

import android.app.Application
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.destinyardiente.tadzkiir.core.data.repository.AppRepository
import org.destinyardiente.tadzkiir.core.data.source.model.*
import org.destinyardiente.tadzkiir.core.data.source.remote.RemoteDataSource
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService
import java.util.*

class QuranViewModel(remote: ApiService) : ViewModel() {
    private val repo = AppRepository(RemoteDataSource(remote))

    private var _surah = MutableLiveData<List<DataSurah>?>()
    val surah: LiveData<List<DataSurah>?> get() = (_surah)

    private var _juz = MutableLiveData<DataJuz?>()
    val juz: LiveData<DataJuz?> get() = (_juz)

    private var _quran = MutableLiveData<Quran?>()
    val quran: LiveData<Quran?> get() = (_quran)

    var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = (_isLoading)

    fun getSurah(){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.postValue(true)
            val response = repo.remote.getSurah()
            if(response.isSuccessful){
                response.body()?.let {surah ->
                    _surah.postValue(surah.data)
                }
                _isLoading.postValue(false)
            }
        }
    }

    fun getJuz(id: Int){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.postValue(true)
            val response = repo.remote.getJuz(id)
            if(response.isSuccessful){
                response.body()?.let {juz ->
                    _juz.postValue(juz.data)
                }
                _isLoading.postValue(false)
            }
        }
    }

    fun getQuranSurah(id: Int){
        _isLoading.value = true
        _quran.value = null
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.postValue(true)
            val response = repo.remote.getQuranSurah(id)
            if(response.isSuccessful){
                response.body()?.let {surah ->
                    _quran.postValue(surah)
                }
                _isLoading.postValue(false)
            }
        }
    }
}