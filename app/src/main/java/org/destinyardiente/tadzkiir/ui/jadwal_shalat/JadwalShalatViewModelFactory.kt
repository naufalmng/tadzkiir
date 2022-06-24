package org.destinyardiente.tadzkiir.ui.jadwal_shalat

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.destinyardiente.tadzkiir.core.data.repository.AppRepository
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class JadwalShalatViewModelFactory(private val api: ApiService,private val applicationContext: Application? = null): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JadwalShalatViewModel::class.java)){
            return JadwalShalatViewModel(api,applicationContext) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}