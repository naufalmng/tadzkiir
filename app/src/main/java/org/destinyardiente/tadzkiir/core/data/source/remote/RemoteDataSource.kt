package org.destinyardiente.tadzkiir.core.data.source.remote

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService
import org.destinyardiente.tadzkiir.core.data.source.remote.request.LoginRequest
import org.destinyardiente.tadzkiir.core.data.source.remote.request.RegisterRequest
import org.destinyardiente.tadzkiir.core.data.source.remote.response.BaseApiResponse
import org.destinyardiente.tadzkiir.core.data.source.remote.response.LoginResponse
import org.destinyardiente.tadzkiir.utils.NetworkResult
import retrofit2.http.QueryMap
import retrofit2.http.Url

class RemoteDataSource(private val api: ApiService): BaseApiResponse() {

    suspend fun getDaftarMenu() = api.getDaftarMenu()
    suspend fun register(data: RegisterRequest): Flow<NetworkResult<JsonObject>> {
        return flow{
            emit(safeApiCall { api.register(data)})
        }.flowOn(Dispatchers.IO)
    }
    suspend fun login(data: LoginRequest): Flow<NetworkResult<LoginResponse>> {
        return flow{
            emit(safeApiCall { api.login(data) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getDzikirPetangKubro() = api.getDzikirPetangKubro()
    suspend fun getDzikirPetangSugro() = api.getDzikirPetangSugro()
    suspend fun getDzikirPagiSugro() = api.getDzikirPagiSugro()
    suspend fun getDzikirPagiKubro() = api.getDzikirPagiKubro()
    suspend fun getSurah() = api.getSurah()
    suspend fun getJuz(id: Int) = api.getJuz(id)
    suspend fun getQuranSurah(id: Int) = api.getQuranSurah(id)
    suspend fun getJadwalShalat(@QueryMap param: MutableMap<String?,String?>?) = api.getJadwalShalat(param)
}