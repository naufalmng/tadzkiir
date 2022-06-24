package org.destinyardiente.tadzkiir.ui.quran

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.destinyardiente.tadzkiir.core.data.repository.AppRepository
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class QuranViewModelFactory(private val api: ApiService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuranViewModel::class.java)){
            return QuranViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}