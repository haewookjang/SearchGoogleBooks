package io.hwjang.app.googlebooks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import io.hwjang.app.googlebooks.base.BaseViewModel

class SplashViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _next = MutableLiveData(false)
    val next: LiveData<Boolean>
        get() = _next

    fun load() {
        viewModelScope.launch {
            delay(2500L)
            _next.postValue(true)
        }
    }
}