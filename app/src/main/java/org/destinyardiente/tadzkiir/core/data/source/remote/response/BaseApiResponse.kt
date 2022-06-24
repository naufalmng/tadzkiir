package org.destinyardiente.tadzkiir.core.data.source.remote.response

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.destinyardiente.tadzkiir.utils.NetworkResult
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            val body = response.body()
            Log.i("BaseApiResponse",response.code().toString())
            Log.i("BaseApiResponse",response.message().toString())
//            Log.i("BaseApiResponse",response.errorBody()?.source()?.buffer?.snapshot()?.utf8().toString())
            if(response.isSuccessful){
                Log.i("BaseApiResponse",response.code().toString())
                if (response.code() == 200) {
                    body?.let {
                        return NetworkResult.Success(body)
                    }
                }
                if(response.code() == 201){
                    return NetworkResult.Success(body)
                }
            }
            if(response.code() == 401){
                return NetworkResult.Error("Email atau password yang anda masukan salah !",null)
            }
            if(response.code() == 422){
                return NetworkResult.Error("Email ini telah terdaftar",null)
            }
            val errMessage = Gson().fromJson<T>(response.errorBody()?.charStream()?.readText(), JsonObject::class.java)
            return NetworkResult.Error("${response.code()}", null)
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage", null)

}