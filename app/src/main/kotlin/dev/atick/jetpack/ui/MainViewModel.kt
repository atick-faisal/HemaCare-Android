package dev.atick.jetpack.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _nSelectedImages = MutableStateFlow(0)
    val nSelectedImages: StateFlow<Int>
        get() = _nSelectedImages

    fun setSelectedImages(imageUris: List<Uri>) {
        _nSelectedImages.value = imageUris.size
    }

}