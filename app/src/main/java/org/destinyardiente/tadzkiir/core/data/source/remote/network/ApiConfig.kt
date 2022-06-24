package org.destinyardiente.tadzkiir.core.data.source.remote.network

import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.destinyardiente.tadzkiir.core.MyApplication
import org.destinyardiente.tadzkiir.ui.main.MainActivity
import org.destinyardiente.tadzkiir.utils.Constant
import org.destinyardiente.tadzkiir.utils.Constant.KEY_API_SHALAT
import org.destinyardiente.tadzkiir.utils.Helper.hasNetwork
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiConfig {
    val provideDzikrApi: ApiService get() = client.create(ApiService::class.java)
    val provideQuranApi: ApiService get() = client2.create(ApiService::class.java)
    val provideShalatApi: ApiService get() = client3.create(ApiService::class.java)

    private val client: Retrofit
    get(){
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val cacheSize = (5 * 1024 * 1024).toLong()
        val appCache = Cache(MyApplication.context!!.cacheDir,cacheSize)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder()
//            .cache(appCache)
            .addInterceptor(interceptor)
//            .addInterceptor {chain ->
//                // Get the request from the chain.
//                var request = chain.request()
//                /*
//                *  If there is Internet, get the cache that was stored 5 seconds ago.
//                *  If the cache is older than 5 seconds, then discard it,
//                *  and indicate an error in fetching the response.
//                *  The 'max-age' attribute is responsible for this behavior.
//                */
//                request = if(hasNetwork(MyApplication.context!!)!!)
//                    request.newBuilder().header("Cache-Control","public, max-age="+5).build()
//                else
//                /*
//                    *  If there is no Internet, get the cache that was stored 7 days ago.
//                    *  If the cache is older than 7 days, then discard it,
//                    *  and indicate an error in fetching the response.
//                    *  The 'max-stale' attribute is responsible for this behavior.
//                    *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
//                    */
//                    request.newBuilder().header("Cache-Control","public, only-if-cached, max-stale="+ 60 * 60 * 24 * 7).build()
//                chain.proceed(request)
//            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(Constant.BASE_URL)
            .build()
    }

    private val client2: Retrofit
    get(){
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(Constant.QURAN_API_BASE_URL)
            .build()
    }

    private val client3: Retrofit
    get(){
        val gson = GsonBuilder()
            .setLenient()
            .create()
//
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor( Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $KEY_API_SHALAT")
                    .build()

                chain.proceed(newRequest)
            })
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(Constant.JADWAL_SHALAT_API)
            .build()
    }
}




