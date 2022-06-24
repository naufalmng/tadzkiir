package org.destinyardiente.tadzkiir.utils

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Constant {
    const val BASE_URL = "https://tadzkir.herokuapp.com/api/"
    const val QURAN_API_BASE_URL = "https://api.quran.sutanlab.id/"
    const val JADWAL_SHALAT_API = "https://api.m3o.com/v1/prayer/"
    const val DZIKR_TABLE = "dzikir"
    const val AYAH_TABLE = "ayah"
    const val DZIKR_MENU_TABLE = "dzikr_menu"
    const val QURAN_TABLE = "quran"
    const val KEY_API_SHALAT = "NjQzN2RhMWEtMmEzYS00ZGZkLWI1ZDQtZGE5NDkwOTkwN2Jk"
//
//    const val LANDING3 = "https://i.ibb.co/X2Q9T6F/rachid-oucharia-2d1-OSHk-HXM-unsplash-1.png"
//    const val LANDING2 = "https://i.ibb.co/qY2fQBC/mhrezaa-to-JMh-Rt-At-SE-unsplash-1.png"
//    const val LANDING1 = "https://i.ibb.co/6gdr8Xv/masjid-pogung-dalangan-GCl-YQv8-I3-So-unsplash.png"
}