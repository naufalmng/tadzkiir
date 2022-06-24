package org.destinyardiente.tadzkiir.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.destinyardiente.tadzkiir.core.data.repository.AppRepository
import org.destinyardiente.tadzkiir.core.data.source.model.DataDaftarMenu
import org.destinyardiente.tadzkiir.core.data.source.model.DataDaftarDzikir
import org.destinyardiente.tadzkiir.core.data.source.remote.RemoteDataSource
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService

class HomeViewModel(remote: ApiService) : ViewModel() {
    private val repo = AppRepository(RemoteDataSource(remote))

    private var _daftarMenu = MutableLiveData<List<DataDaftarMenu>?>()
    val daftarMenu: LiveData<List<DataDaftarMenu>?> get() = (_daftarMenu)

    private var _dataDzikir = MutableLiveData<ArrayList<DataDaftarDzikir>?>()
    val dataDzikir: LiveData<ArrayList<DataDaftarDzikir>?> get() = (_dataDzikir)

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = (_isLoading)

    // ROOM DATABASE



    // REMOTE DATABASE
    fun getDzikirPetangKubro(){
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val response = repo.remote.getDzikirPetangKubro()
            if(response.isSuccessful){
                response.body()?.let {
                    _dataDzikir.postValue(it.data)
                }
                _isLoading.postValue(false)
                Log.d("HomeViewModel: ", "getDataDaftarDzikir: ${response.body()?.data} ")
            }
        }
    }

    fun getDzikirPetangSugro(){
        viewModelScope.launch(Dispatchers.IO)  {
            _isLoading.postValue(true)
            val response = repo.remote.getDzikirPetangSugro()
            if(response.isSuccessful){
                response.body()?.let {
                    _dataDzikir.postValue(it.data)
                }
                _isLoading.postValue(false)
                Log.d("HomeViewModel: ", "getDataDaftarDzikir: ${response.body()?.data} ")
            }
        }
    }
    fun getDzikirPagiSugro(){
        viewModelScope.launch(Dispatchers.IO)  {
            _isLoading.postValue(true)
            val response = repo.remote.getDzikirPagiSugro()
            if(response.isSuccessful){
                response.body()?.let {
                    _dataDzikir.postValue(it.data)
                }
                _isLoading.postValue(false)
                Log.d("HomeViewModel: ", "getDataDaftarDzikir: ${response.body()?.data} ")
            }
        }
    }
    fun getDzikirPagiKubro(){
        viewModelScope.launch(Dispatchers.IO)  {
            _isLoading.postValue(true)
            val response = repo.remote.getDzikirPagiKubro()
            if(response.isSuccessful){
                response.body()?.let {
                    _dataDzikir.postValue(it.data)
                }
                _isLoading.postValue(false)
                Log.d("HomeViewModel: ", "getDataDaftarDzikir: ${response.body()?.data} ")
            }
        }
    }

    fun getDaftarMenu() {
        viewModelScope.launch(Dispatchers.IO)  {
            _isLoading.postValue(true)
            val response = repo.remote.getDaftarMenu()
            if (response.isSuccessful) {
                response.body()?.let {
                    _daftarMenu.postValue(it.data)
                }
                
                _isLoading.postValue(false)
                Log.d("HomeViewModel: ", "getDaftarMenu: ${response.body()?.data} ")
            } else {
                Log.e("HomeViewModel: ", response.message().toString())
            }
        }
    }
}