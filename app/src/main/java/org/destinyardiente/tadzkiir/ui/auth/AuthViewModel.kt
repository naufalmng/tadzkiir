package org.destinyardiente.tadzkiir.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.destinyardiente.tadzkiir.core.data.repository.AppRepository
import org.destinyardiente.tadzkiir.core.data.source.remote.RemoteDataSource
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService
import org.destinyardiente.tadzkiir.core.data.source.remote.request.LoginRequest
import org.destinyardiente.tadzkiir.core.data.source.remote.request.RegisterRequest
import org.destinyardiente.tadzkiir.core.data.source.remote.response.LoginResponse
import org.destinyardiente.tadzkiir.utils.NetworkResult

class AuthViewModel(remote: ApiService): ViewModel() {
    private val repo = AppRepository(RemoteDataSource(remote))

    var registerRequest  : MutableLiveData<NetworkResult<JsonObject>> = MutableLiveData()
    var loginRequest  : MutableLiveData<NetworkResult<LoginResponse>> = MutableLiveData()

    fun register(data: RegisterRequest){
        viewModelScope.launch {
            repo.remote.register(data).collect { value ->
                registerRequest.value = value
            }
        }
    }

    fun login(data: LoginRequest){
        viewModelScope.launch {
            repo.remote.login(data).collect { value->
                loginRequest.postValue(value)
            }
        }
    }
}