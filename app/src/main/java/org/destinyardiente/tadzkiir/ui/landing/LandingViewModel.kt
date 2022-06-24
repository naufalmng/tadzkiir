package org.destinyardiente.tadzkiir.ui.landing

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class LandingViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = (_isLoading)

    fun setSliderData(imgUrl: Int, imageViewTarget: ImageView) {
        _isLoading.value = true
        viewModelScope.launch {
            Glide.with(imageViewTarget.context)
                .load(imgUrl)
                .into(imageViewTarget)
            _isLoading.value = false
        }

    }
}