package org.destinyardiente.tadzkiir.core.data.source.remote.network

import com.google.gson.JsonObject
import org.destinyardiente.tadzkiir.core.data.source.model.*
import org.destinyardiente.tadzkiir.core.data.source.remote.request.LoginRequest
import org.destinyardiente.tadzkiir.core.data.source.remote.request.RegisterRequest
import org.destinyardiente.tadzkiir.core.data.source.remote.response.LoginResponse
import org.destinyardiente.tadzkiir.utils.Constant.KEY_API_SHALAT
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @Headers("Content-Type: application/json", "Accept:application/json")
    @GET("menus")
    suspend fun getDaftarMenu(): Response<DaftarMenu>

    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("register")
    suspend fun register(@Body data: RegisterRequest): Response<JsonObject>

    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("login")
    suspend fun login(@Body data: LoginRequest): Response<LoginResponse>

    @Headers("Content-Type: application/json", "Accept:application/json")
    @GET("getDzikirPetangKubro")
    suspend fun getDzikirPetangKubro(): Response<DaftarDzikir>

    @Headers("Content-Type: application/json", "Accept:application/json")
    @GET("getDzikirPetangSugro")
    suspend fun getDzikirPetangSugro(): Response<DaftarDzikir>

    @Headers("Content-Type: application/json", "Accept:application/json")
    @GET("getDzikirPagiSugro")
    suspend fun getDzikirPagiSugro(): Response<DaftarDzikir>

    @Headers("Content-Type: application/json", "Accept:application/json")
    @GET("getDzikirPagiKubro")
    suspend fun getDzikirPagiKubro(): Response<DaftarDzikir>

    @GET("surah")
    suspend fun getSurah(): Response<Surah>

    @GET("juz/{id}")
    suspend fun getJuz(@Path("id") id: Int): Response<Juz>


    @GET("surah/{id}")
    suspend fun getQuranSurah(@Path("id") id: Int): Response<Quran>

    @Headers("Content-Type: application/json")
    @GET("Times")
    suspend fun getJadwalShalat(@QueryMap param: MutableMap<String?,String?>?) : Response<Shalat>
}