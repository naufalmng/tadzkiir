@file:Suppress("UNCHECKED_CAST")

package org.destinyardiente.tadzkiir.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiService
import java.lang.IllegalArgumentException

class AuthViewModelFactory(private val api: ApiService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}